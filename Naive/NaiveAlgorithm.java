import java.util.ArrayList;

public class NaiveAlgorithm {

    private String Text;//строка, содержащая текст
    private String Pattern;//строка, содержащая шаблон

    //Конструктор
    NaiveAlgorithm(String Text, String Pattern){
        this.Text=Text;
        this.Pattern=Pattern;
    }

    //Метод для поиска подстроки в строке
    public ArrayList<Integer> Search(){
        ArrayList<Integer> Answer = new ArrayList<Integer>(); //список индексов вхождений подстроки в строку

        for (int i = 0; i <= Text.length() - Pattern.length(); ++i) { //перебор всех возможных сдвигов по строке
            if(isEqualPart(i)) //если части совпали
                Answer.add(i); //добавляем в ответ
        }

        if (Answer.isEmpty()) //если нет ответа, то добавляем -1
            Answer.add(-1);

        return Answer;
    }

    //Метод для сравнения шаблона и части строки начиная с индекса IndexT
    private boolean isEqualPart(int IndexInText){
        int j=0; //текущий индекс в шаблоне
        while(j<Pattern.length() && isEqualChar(IndexInText+j, j)) //попарное сравнение символов шаблона и части строки
            j++;
        return j==Pattern.length();
    }

    //Метод для сравнения соответствующих символов из строки и шаблона
    private boolean isEqualChar(int IndexInText, int IndexInPattern){
        return Pattern.charAt(IndexInPattern)==T.charAt(IndexInTextT);
    }
}
