
1-איך יוצרים תיקייה בגאווה?
-
הסבר קצר 
-
בכדי ליצור תיקייה חדשה בג’אווה, אפשר להשתמש במחלקה File שמגיעה מהחבילה java.io.
מה שעושים זה מגדירים אובייקט חדש מסוג File עם השם של התיקייה שאנחנו רוצים, 
ואז קוראים לפעולה mkdir() שמנסה ליצור אותה.
-
דוגמת קוד פשוטה..
-
import java.io.File;
public class Student{
public static void main (String [] args){
String directory = ("Users/soFTECH/ldeaProjects/untitled4);
File folder = new File (directory);
Folder.mkdir();
-
קישור למקור ?
-
https://youtu.be/KJHDO2eiDkU?si=YQaId0ZKOtbgVvHA  

-
2-איך יוצרים קובץ?
-
   הםבר קצר
-
כדי ליצור קובץ חדש בג’אווה, משתמשים במחלקה File מהחבילה java.io.
מגדירים אובייקט חדש עם שם הקובץ, ואז קוראים לפעולה createNewFile()
-
דוגמת קוד פשוטה..
-
import java.io.File;
public class Student{
public static void main(String [] args ){
File file = new File(""Learn java.txt");
try{
if(file.createNewFile()){
System.ouy.println("The file was created successfully.");
}else{
System.out.println("The file was not created");}
catch(Exception error){}}
-
קישור למקור ?
-
https://youtu.be/UpJEjWjLFAE?si=g4xOzAoAkrWJpWm8  

-
3-איך בודקים אם קובץ או תיקיה קיימים?
-
הםבר קצר   
-
כדי לבדוק אם קובץ או תיקייה קיימים, נשתמש ב-class File.
באמצעות המתודה exists() אפשר לבדוק אם הקובץ או התיקייה קיימים במערכת הקבצים.
-
דוגמת קוד פשוטה..
-
import java.io.File;
/*
* Once you have instantiated a File abject we can check
* if the corresponding file/dir actually exists already 
*/
public class Student{
public static void main(String [] args){
File file = new File ("D:/work/myfile.txt");
/*
* Test whether the file or directory denoted by
* this abstract pathname exists.
* /
boolean isExist =file.exists();
System.out.println(file.getAbsolutePath()+"is exist ? = " + isExist );}}
-
קישור למקור?
-
https://youtu.be/eQL5gOkZcIA?si=RYtZNHM39VnEKRU5  
-
4-איך מציגים את תוכן תיקייה?
-
הםבר קצר   
-
כדי להציג את תוכן תיקייה, נשתמש ב-class File ונתחיל בשיטה list().
השיטה מחזירה מערך של שמות הקבצים בתיקייה.
-
דוגמת קוד פשוטה..
-
import java.io.File;
public class Student{
public static void main (String [] args){
try{
File myfile = new File ("C:\\Users\\A\\Desktop\\test.txt");
if(myfile.creatNewFile()){
System.out.println("File Name:" + myfile.getName());
System.out.println("Absolute path :" + myfile.getAbsolutepath());
System.out.println("Writeable :" + myfile.canRead());
System.out.println("File Size In Bytes :" + myfile.length());
}
else{
System.out.println("File Is Already Exsits !");}}
catch (Exception e ){
System.out.println(e.getMessage());
}}
-
קישור למקור ?
-
https://youtu.be/qaYfVIFik9Y?si=kEB3XBwSUvBSlNwk 
-
5-מה ההבדל בין File ל-Path?
-
הםבר קצר   
-
File: מחלקה ישנה מ־java.io, משמשת לעבודה עם קבצים ותיקיות.
Path: מחלקה חדשה יותר מ־java.nio.file, עובדת עם נתיבים (Paths) ומציעה שליטה וגמישות רבה יותר.
-
דוגמת קוד פשוטה..
-
import java.nio.file.path;
import java.nio.file.paths;
import java.io.File;
public class Student{
//USING PATH
public static void main(String [] args){
Path path = Paths.get("folder/file.txt");
System.out.println("path : " + path.toString());
//USING FILE 
File file = new File ("test/txt");
System.out.println("File : " + file.getAbsolutePath)
}}

-
קישור למקור ?
-
https://www.javaguides.net/2023/11/difference-between-path-and-file-in-java-nio.html 


