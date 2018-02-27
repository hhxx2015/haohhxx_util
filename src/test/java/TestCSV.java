import org.haohhxx.util.io.CSVReader;
import org.junit.Test;

import java.util.List;

public class TestCSV {

    @Test
    public void testCSV(){
        String pathCSV = "C:/Users/Administrator/Desktop/asasasa.csv";
        CSVReader csvReader = CSVReader.getCSVReader(pathCSV);
        for(List<String> cline : csvReader){
            System.out.println(cline);
        }
    }


}
