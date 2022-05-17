package com.moodle.moodle;

import com.moodle.moodle.exception.CommonException;
import com.moodle.moodle.model.PatternQuestions;
import com.moodle.moodle.service.GenerateXmlService;
import com.moodle.moodle.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class MoodleApplication  extends JFrame {

	@Autowired
	private GenerateXmlService generateXmlService;
	private String officeFilePath;
	private String xmlFilePath;
	private PatternQuestions questionPattern = PatternQuestions.POINT;

	public MoodleApplication() {
		initUI();
	}

	private void initUI() {

		JPanel flow = new JPanel();
		flow.setLayout(new VerticalLayout());

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filterDocx = new FileNameExtensionFilter(
				"Word", "docx");

		FileNameExtensionFilter filterXml = new FileNameExtensionFilter(
				"Xml", "xml");

		fileChooser.setAcceptAllFileFilterUsed(false);


		var btnOpenDir = new JButton("Открыть файл");
		btnOpenDir.setMinimumSize(new Dimension(30, 50));

		var btnSaveFile = new JButton("Сохранить файл");
		btnOpenDir.setMinimumSize(new Dimension(30, 50));

		var selectedFile = new JLabel();

		btnOpenDir.addActionListener(e -> {
			fileChooser.setDialogTitle("Выбор директории");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setFileFilter(filterDocx);

			int result = fileChooser.showOpenDialog(MoodleApplication.this);
			if (result == JFileChooser.APPROVE_OPTION ) {
				officeFilePath = fileChooser.getSelectedFile().getPath();
				JOptionPane.showMessageDialog(MoodleApplication.this,
						officeFilePath);
				selectedFile.setText(officeFilePath);
			}

		});

		btnSaveFile.addActionListener(e -> {
			fileChooser.setDialogTitle("Сохранение файла");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setFileFilter(filterXml);
			fileChooser.setSelectedFile(
					new File(FilenameUtils.removeExtension(new File(officeFilePath).getName()) + ".xml")
			);

			int result = fileChooser.showSaveDialog(MoodleApplication.this);
			if (result == JFileChooser.APPROVE_OPTION ) {
				xmlFilePath = fileChooser.getSelectedFile().getPath();
				try {
					generateXmlService.generate(officeFilePath,xmlFilePath, questionPattern);
					JOptionPane.showMessageDialog(MoodleApplication.this,
							"Файл '" + fileChooser.getSelectedFile() +
									" сохранен");
				} catch (CommonException exception) {
					JOptionPane.showMessageDialog(MoodleApplication.this,
							exception.getErrorMessage());
				} catch (IOException ex) {
					log.error("Open file error ");
				}

			}

		});

		JComboBox<PatternQuestions> answerComboBox = new JComboBox<>();

		JComboBox<PatternQuestions> formatQuestionComboBox = new JComboBox<>(PatternQuestions.values());
		formatQuestionComboBox.setSize(100, 50);
		formatQuestionComboBox.addActionListener(e -> {
			JComboBox box = (JComboBox)e.getSource();
			PatternQuestions item = (PatternQuestions)box.getSelectedItem();
			questionPattern = item;
			answerComboBox.setModel(new DefaultComboBoxModel<>(Arrays.stream(PatternQuestions.values())
					.filter(patternQuestions -> patternQuestions != questionPattern).toArray(PatternQuestions[]::new)));

		});
		flow.add(btnOpenDir);
		flow.add(selectedFile);
		flow.add(formatQuestionComboBox);
		flow.add(btnSaveFile);

		createLayout(flow);

		setTitle("Moodle generator");
		setSize(280, 180);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createLayout(JComponent... arg) {

		var pane = getContentPane();
		var gl = new GroupLayout(pane);
		pane.setLayout(gl);

		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addComponent(arg[0])
		);

		gl.setVerticalGroup(gl.createSequentialGroup()
				.addComponent(arg[0])
		);
	}

	public static void main(String[] args) {

		var ctx = new SpringApplicationBuilder(MoodleApplication.class)
				.headless(false).run(args);

		EventQueue.invokeLater(() -> {

			var ex = ctx.getBean(MoodleApplication.class);
			ex.setVisible(true);
		});
	}

}
