import org.example.Function_Search;
import org.example.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Function_SearchTest {
    private GUI gui;
    private Function_Search functionSearch;

    @BeforeEach
    public void setUp() {
        gui = new GUI();
        functionSearch = new Function_Search(gui);
        // Set some default text in the text area for searching
        gui.textArea.setText("This is a simple text editor for testing search functionality.");
    }

    @Test
    public void testSearchTextFound() {
        // Simulate user input for the search term
        String searchTerm = "simple";

        // Search for the term
        functionSearch.searchAndSelectText(searchTerm);

        // Check if the text was selected
        String selectedText = gui.textArea.getSelectedText();
        assertEquals(searchTerm, selectedText, "Selected text should match the search term.");
    }

    @Test
    public void testSearchTextNotFound() {
        // 设置搜索词
        String searchTerm = "nonexistent";

        // 确保文本区有一些内容可以搜索（可以是空白或不包含搜索词）
        gui.textArea.setText("This is some sample text that does not contain the term.");

        // 执行搜索
        functionSearch.searchAndSelectText(searchTerm);

        // 检查是否没有选中的文本
        String selectedText = gui.textArea.getSelectedText();

        // 当搜索词未找到时，selectedText 应该为 null
        assertNull(selectedText, "No text should be selected if the term is not found.");

        // 可以选择打印出调试信息，便于进一步定位问题
        if (selectedText == null) {
            System.out.println("Search term not found as expected.");
        } else {
            System.out.println("Unexpected selection: " + selectedText);
        }
    }

    @Test
    public void testContinueSearchFound() {
        // Simulate user input for the search term
        String searchTerm = "test";

        // First search to find the first occurrence
        functionSearch.searchAndSelectText(searchTerm);

        // Continue search to find the next occurrence
        functionSearch.continueSearch();

        // Check if the second occurrence was found and selected
        String selectedText = gui.textArea.getSelectedText();
        assertEquals(searchTerm, selectedText, "Selected text should match the search term.");
    }
}
