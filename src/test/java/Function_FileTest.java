import org.example.Function_File;
import org.example.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Function_FileTest {
    private GUI gui;
    private Function_File functionFile;

    @BeforeEach
    public void setUp() {
        gui = new GUI();
        functionFile = new Function_File(gui);
    }

    @Test
    public void testNewFile() {
        functionFile.newFile();
        assertEquals("", gui.textArea.getText(), "New file should have empty text.");
        assertNull(functionFile.fileName, "New file should not have a file name.");
    }

    @Test
    public void testSaveFile() throws IOException {
        // 创建一个临时文件用于模拟保存
        File tempFile = File.createTempFile("testFile", ".txt");

        // 设置 Function_File 类中的文件名和路径
        functionFile.fileName = tempFile.getName();
        functionFile.fileAddress = tempFile.getParent();

        // 在文本区域中写入一些文本
        String expectedText = "This is a test file.";
        gui.textArea.setText(expectedText);

        // 保存文件
        functionFile.SaveFile();

        // 读取保存后的文件内容
        String actualContent = Files.readString(tempFile.toPath());

        // 检查文件内容是否与文本区域中的文本一致
        assertEquals(expectedText, actualContent.trim(), "File content should match the text in text area.");

        // 确保在测试结束后删除该文件
        tempFile.deleteOnExit();
    }
    @Test
    public void testOpenTextFile() throws IOException {
        // Create a temporary text file
        File tempFile = File.createTempFile("testOpenFile", ".txt");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("Hello, this is a test file.");
        }

        // Set file details in Function_File
        functionFile.fileName = tempFile.getName();
        functionFile.fileAddress = tempFile.getParent();

        // Simulate opening the file
        functionFile.openTextFile(tempFile);

        // Check if the text was loaded into the text area correctly
        assertEquals("Hello, this is a test file.\n", gui.textArea.getText(), "Text area should contain the file's content.");
    }
}
