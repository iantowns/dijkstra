import java.io.*

public class GraphCheck { 
    public int check(int x, int y) { 
        try {
            /*  Sets up a file reader to read the file passed on the command
                line one character at a time */
            FileReader input = new FileReader("us.out.txt"); //probably will add in string directly
            /* Filter FileReader through a Buffered read to read a line at a
               time */
            BufferedReader bufRead = new BufferedReader(input);
            String line;    // String that holds current file line
            // Read first line
            for(int i = 0; i < x; ++i) {
                if(line == null) throw new NullPointerException("not in graph matrix");
                bufRead.readLine();
            }
            line = bufRead.readLine();
            // Read through file one line at time. Print line # and line
            String[] parts = line.split(" ");
            return Integer.parseInt(parts[y - 1]);      
        }
        catch (ArrayIndexOutOfBoundsException e){
        System.out.print("exception1");
            /* If no file was passed on the command line, this expception is
            generated. A message indicating how to the class should be
            called is displayed */
            System.out.println("Usage: java ReadFile filename\n");          
        }
        catch (IOException e){
        System.out.print("exception2");
            // If another exception is generated, print a stack trace
            e.printStackTrace();
        }
    }
}