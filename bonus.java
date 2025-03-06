import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Main 
{   
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        HashMap<String,Library> libraries = new HashMap<>();
        HashMap<String,Category> categories = new HashMap<>();
        HashMap<String,Student> students = new HashMap<>();
        HashMap<String,Staff> staffs = new HashMap<>();
        while (true) 
        {
            String func = scanner.nextLine();
            if (func.compareTo("finish") == 0) 
            {
                break;    
            }
            //report-penalties-sum
            else if (func.compareTo("report-penalties-sum") == 0) {
                int res = 0;
                for(Staff i : staffs.values()){
                    res += i.getStaff_debt();
                }
                for(Student i : students.values()){
                    res += i.getStudent_debt();
                }
                System.out.println(res);
            }
            else
            {
                int makan_hashtag = func.indexOf("#");
                String s1 = func.substring(0  , makan_hashtag );
                func = func.replace(s1 + "#" , "");
                String[] edits = func.split("\\|");

                //add library
                if (s1.compareTo("add-library") == 0) 
                {
                    int miz = Integer.valueOf(edits[3]) , sal = Integer.valueOf(edits[2]);
                    Library newl = new Library(edits[0], edits[1], sal , miz , edits[4]);
                    if (libraries.containsKey(edits[0])) 
                    {
                        System.out.println("duplicate-id");    
                    }
                    else
                    {
                        libraries.put(edits[0], newl);
                        System.out.println("success");
                    }
                }
                
                //add-category
                else if (s1.compareTo("add-category") == 0 ) 
                {
                    if (categories.containsKey(edits[0])) 
                    {
                        System.out.println("duplicate-id");    
                    }
                    else
                    {
                        Category newc = new Category(edits[0], edits[1]);
                        categories.put(edits[0], newc);
                        System.out.println("success");
                    }
                }

                //add-book                  //EX : add-book#B001|Book1|Somebody|Springer|2020|3|C001|L001
                else if (s1.compareTo("add-book") == 0) 
                {  
                    int sal = Integer.valueOf(edits[4]) , chap = Integer.valueOf(edits[5]);
                    if (libraries.containsKey(edits[7]) && libraries.get(edits[7]).getBooks().containsKey(edits[0])) 
                    {
                        System.out.println("duplicate-id");
                    }
                    else if (!(libraries.containsKey(edits[7]) && (categories.containsKey(edits[6]) || edits[6].compareTo("null") == 0))) 
                    {
                        System.out.println("not-found");
                    }
                    else
                    {
                        Book newb = new Book(edits[0], edits[1], edits[2] , edits[3] , sal , chap , edits[6] , edits[7]); 
                        libraries.get(edits[7]).getBooks().put(edits[0], newb);
                        System.out.println("success");
                    }
                }

                //edit-book                 //EX : edit-book#B001|L001|-|-|IEEE|2021|4|-
                else if (s1.compareTo("edit-book") == 0) 
                {
                    int sal = -1 , copy = -1;
                    if (edits[6].compareTo("-") != 0) 
                    {
                        copy = Integer.valueOf(edits[6]);
                    }
                    if (edits[5].compareTo("-") != 0) 
                    {
                        sal = Integer.valueOf(edits[5]);
                    }
                    if (!(categories.containsKey(edits[7]) || (edits[7].compareTo("-") == 0) || (edits[7].compareTo("null") == 0)) || !(libraries.containsKey(edits[1]) && libraries.get(edits[1]).getBooks().containsKey(edits[0])))
                    {
                        System.out.println("not-found");
                    }
                    else
                    {
                        Book changebBook = libraries.get(edits[1]).getBooks().get(edits[0]);
                        if (edits[2].compareTo("-") != 0) 
                        {
                            changebBook.setBook_name(edits[2]);
                        }
                        if (edits[3].compareTo("-") != 0) 
                        {
                            changebBook.setBook_writer(edits[3]);
                        }
                        if (edits[4].compareTo("-") != 0) 
                        {
                            changebBook.setBook_entesharat(edits[4]);
                        }
                        if (edits[5].compareTo("-") != 0) 
                        {
                            changebBook.setBook_sal_chap(sal);
                        }
                        if (edits[6].compareTo("-") != 0) 
                        {
                            changebBook.setBook_tedad_copy(copy);
                        }
                        if (edits[7].compareTo("-") != 0) 
                        {
                            changebBook.setBook_category(edits[7]);
                        }
                        System.out.println("success");
                    }     
                }
        
                //remove book                //EX : remove-book#B001|L001
                else if (s1.compareTo("remove-book") == 0) 
                {
                    if (libraries.containsKey(edits[1]) && libraries.get(edits[1]).getBooks().containsKey(edits[0])) 
                    {
                        if (libraries.get(edits[1]).getBooks().get(edits[0]).getBook_borrows() > 0) 
                        {
                            System.out.println("not-allowed");
                        }
                        else
                        {
                            libraries.get(edits[1]).getBooks().remove(edits[0]);
                            System.out.println("success");
                        }
                    }
                    else
                    {
                        System.out.println("not-found");
                    }
                }

                //add thesis                 // EX : add-thesis#T01|Thesis1|StudentName|ProfessorName|1399|null|L003
                else if (s1.compareTo("add-thesis") == 0) 
                {
                    int sal = Integer.valueOf(edits[4]);
                    if (libraries.containsKey(edits[6]) && libraries.get(edits[6]).getthesis().containsKey(edits[0])) 
                    {
                        System.out.println("duplicate-id");
                    }
                    else if (!(libraries.containsKey(edits[6]) && (categories.containsKey(edits[5]) || edits[5].compareTo("null") == 0))) 
                    {
                        System.out.println("not-found");
                    }
                    else 
                    {
                        Thesis newb = new Thesis(edits[0], edits[1], edits[2] , edits[3] , sal , edits[5] , edits[6]);
                        libraries.get(edits[6]).getthesis().put(edits[0], newb);
                        System.out.println("success");
                    }
                }

                //edit thesis                  // EX : edit-thesis#T01|L004|ThesisNew|-|-|1400|null
                else if (s1.compareTo("edit-thesis") == 0) 
                {
                    int sal = -1;
                    if (edits[5].compareTo("-") != 0) 
                    {
                        sal = Integer.valueOf(edits[5]);
                    }
                    if (libraries.containsKey(edits[1]) && libraries.get(edits[1]).getthesis().containsKey(edits[0])) 
                    {
                        if (categories.containsKey(edits[6]) || (edits[6].compareTo("null") == 0) || (edits[6].compareTo("-") == 0)) 
                        {
                            Thesis changebtThesis = libraries.get(edits[1]).getthesis().get(edits[0]);
                            System.out.println("success");
                            if (edits[2].compareTo("-") != 0) 
                            {
                                changebtThesis.setThesis_name(edits[2]);
                            }
                            if (edits[3].compareTo("-") != 0) 
                            {
                                changebtThesis.setThesis_student(edits[3]);    
                            }
                            if (edits[4].compareTo("-") != 0) 
                            {
                                changebtThesis.setThesis_ostad(edits[4]);    
                            }
                            if (edits[5].compareTo("-") != 0) 
                            {
                                changebtThesis.setThesis_sal(sal);    
                            }
                            if (edits[6].compareTo("-") != 0) 
                            {
                                changebtThesis.setThesis_category(edits[6]);
                            } 
                        }  
                        else
                        {
                            System.out.println("not-found");
                        } 
                    }
                    else
                    {
                        System.out.println("not-found");
                    }
                    
                }
                
                //remove thesis                // EX : remove-thesis#T01|L001
                else if (s1.compareTo("remove-thesis") == 0) 
                {
                    if (libraries.containsKey(edits[1]) && libraries.get(edits[1]).getthesis().containsKey(edits[0])) 
                    {
                        if (libraries.get(edits[1]).getthesis().get(edits[0]).getisborrowed()) 
                        {
                            System.out.println("not-allowed");
                        }
                        else
                        {
                            libraries.get(edits[1]).getthesis().remove(edits[0]);
                            System.out.println("success");
                        }
                    }
                    else
                    {
                        System.out.println("not-found");
                    }
                }
        
                //add student                 // EX : add-student#14010011234|PassWd|Test|Testi|0123456789|1382|Tehran,Mirdamad
                else if (s1.compareTo("add-student") == 0) 
                {
                    int sal = 0;
                    if (edits[5].compareTo("-") != 0) 
                    {
                        sal = Integer.valueOf(edits[5]);
                    }
                    if (students.containsKey(edits[0])) 
                    {
                        System.out.println("duplicate-id");
                    }
                    else
                    {
                        Student news = new Student(edits[0], edits[1] , edits[2] , edits[3] , edits[4] , sal , edits[6]);
                        students.put(edits[0], news);
                        System.out.println("success");   
                    }
                }
            
                //edit student                  //EX : edit-student#14010011234|NewPass|-|-|-|1383|-
                else if (s1.compareTo("edit-student") == 0) 
                {
                    int sall = -1;
                    if(edits[5].compareTo("-") != 0)
                    {
                        sall = Integer.valueOf(edits[5]);
                    }
                    if (students.containsKey(edits[0])) 
                    {
                        Student changeStudent = students.get(edits[0]);
                        if (edits[0].compareTo("-") != 0) 
                        {
                            changeStudent.set_student_id(edits[0]);
                        }   
                        if (edits[1].compareTo("-") != 0) 
                        {
                            changeStudent.set_student_password(edits[1]);    
                        }
                        if (edits[2].compareTo("-") != 0) 
                        {
                            changeStudent.set_student_name(edits[2]);    
                        }
                        if (edits[3].compareTo("-") != 0) 
                        {
                            changeStudent.set_student_family(edits[3]);    
                        }
                        if (edits[4].compareTo("-") != 0) 
                        {
                            changeStudent.set_student_kod_meli(edits[4]);
                        }
                        if (edits[5].compareTo("-") != 0) 
                        {
                            changeStudent.set_student_sal_tavalod(sall);    
                        }
                        if (edits[6].compareTo("-") != 0) 
                        {
                            changeStudent.set_student_address(edits[6]);    
                        }
                        System.out.println("success");
                    }
                    else
                    {
                        System.out.println("not-found");
                    }
                }
                
                //remove student            // EX : remove-student#14010011236
                else if (s1.compareTo("remove-student") == 0) 
                {
                    if (students.containsKey(edits[0])) 
                    { 
                        if (students.get(edits[0]).getStudent_borrows() > 0 || students.get(edits[0]).getStudent_debt() > 0) 
                        {
                            System.out.println("not-allowed");       
                        }
                        else{
                            students.remove(edits[0]);
                            System.out.println("success");
                        }
                    }
                    else
                    {
                        System.out.println("not-found");    
                    }
                }
        
                //add staff                 // EX : add-staff#11037845|MyPass|Staff1|Staffi|0198765432|1358|Tehran,Azadi
                else if (s1.compareTo("add-staff") == 0) 
                {
                    int sal = 0;
                    if (edits[5].compareTo("-") != 0) 
                    {
                        sal = Integer.valueOf(edits[5]);
                    }
                    if (staffs.containsKey(edits[0])) 
                    {
                        System.out.println("duplicate-id");
                    }
                    else
                    {
                        Staff news = new Staff(edits[0], edits[2] , edits[1] , edits[3] , edits[4] , sal , edits[6]);
                        staffs.put(edits[0] , news);
                        System.out.println("success");    
                    }           
                }
        
                //edit staff                // EX : edit-staff#11037845|-|-|Family|-|-|Mashhad
                else if (s1.compareTo("edit-staff") == 0) 
                {
                    int sal = 0;
                    if (edits[5].compareTo("-") != 0) 
                    {
                        sal = Integer.valueOf(edits[5]);
                    }
                    if (staffs.containsKey(edits[0])) 
                    {
                        Staff changeStaff = staffs.get(edits[0]);
                        if (edits[0].compareTo("-") != 0) 
                        {
                            changeStaff.set_staff_id(edits[0]);
                        }                        
                        if (edits[1].compareTo("-") != 0) 
                        {
                            changeStaff.set_staff_password(edits[1]);    
                        }
                        if (edits[2].compareTo("-") != 0) 
                        {
                            changeStaff.set_staff_name(edits[2]);    
                        }
                        if (edits[3].compareTo("-") != 0) 
                        {
                            changeStaff.set_staff_family(edits[3]);    
                        }
                        if (edits[4].compareTo("-") != 0) 
                        {
                            changeStaff.set_staff_kod_meli(edits[4]);
                        }
                        if (edits[5].compareTo("-") != 0) 
                        {
                            changeStaff.set_staff_sal_tavalod(sal);    
                        }
                        if (edits[6].compareTo("-") != 0) 
                        {
                            changeStaff.set_staff_address(edits[6]);
                        }
                        System.out.println("success");
                    }
                    else
                    {
                        System.out.println("not-found");
                    }
                }
        
                //remove staff              //EX : remove-staff#11037845
                else if (s1.compareTo("remove-staff") == 0)
                { 
                    if (staffs.containsKey(func)) 
                    {
                        if (staffs.get(edits[0]).getStaff_borrows() > 0 || staffs.get(edits[0]).getStaff_debt() > 0) 
                        {
                            System.out.println("not-allowed");       
                        }
                        else
                        {
                            System.out.println("success");
                            staffs.remove(edits[0]);
                        }
                    }
                    else 
                    {
                        System.out.println("not-found");    
                    }
                }

                //borrow                // EX : borrow#11037845|MyPass|L001|B001|2023-04-03|15:00
                else if (s1.compareTo("borrow") == 0) 
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date = null;
                    try {
                        date = dateFormat.parse(edits[4] + " " + edits[5]);
                    } catch (Exception e) {
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                    boolean isBook = true , isStudent = true , let = true;
                    //check person and password
                    if (let) {
                        if (staffs.containsKey(edits[0])) {
                            isStudent = false;
                        }
                        else if (!students.containsKey(edits[0])) {
                            let = false;
                            System.out.println("not-found");
                        }   
                    }
                    
                    //check password
                    if (let) {
                        if (isStudent && students.get(edits[0]).get_student_password().compareTo(edits[1]) != 0) {
                            let = false;
                            System.out.println("invalid-pass");
                        }
                        else if (!isStudent && staffs.get(edits[0]).get_staff_password().compareTo(edits[1]) != 0) {
                            let = false;
                            System.out.println("invalid-pass");
                        }
                    }
                    
                    //check library and thing
                    if (libraries.containsKey(edits[2])) 
                    {
                        if (libraries.get(edits[2]).getthesis().containsKey(edits[3])) 
                        {
                            isBook = false;
                        }
                        else if (!libraries.get(edits[2]).getBooks().containsKey(edits[3])) 
                        {
                            let = false;
                            System.out.println("not-found");    
                        }    
                    }
                    else
                    {
                        let = false;
                        System.out.println("not-found");
                    }
                    
                    
                    //check allow person
                    if (let) {
                        if (isStudent && students.get(edits[0]).getStudent_borrows() == 3) {
                            let = false;
                            System.out.println("not-allowed");
                        }
                        else if (!isStudent && staffs.get(edits[0]).getStaff_borrows() == 5) {
                            let = false;
                            System.out.println("not-allowed");
                        }
                    }

                    //check source of stuffs
                    if (let) {
                        if (isBook && libraries.get(edits[2]).getBooks().get(edits[3]).getBook_borrows() == libraries.get(edits[2]).getBooks().get(edits[3]).getBook_tedad_copy()) {
                            let = false;
                            System.out.println("not-allowed");
                        }
                        else if (!isBook && libraries.get(edits[2]).getthesis().get(edits[3]).getisborrowed()) {
                            let = false;
                            System.out.println("not-allowed");
                        }
                    }


                    //make new borrow
                    if (let) {
                        Borrow newb = new Borrow(date,edits[0], edits[3], edits[1], edits[2]);
                        newb.setIsBook(isBook);
                        newb.setIsStudent(isStudent);
                        
                        //stuff changes
                        if (isBook) {
                            libraries.get(edits[2]).getBooks().get(edits[3]).setBook_borrows(libraries.get(edits[2]).getBooks().get(edits[3]).getBook_borrows() + 1);
                        }
                        else{
                            libraries.get(edits[2]).getthesis().get(edits[3]).setIsborrowed(true);
                        }


                        //person changes
                        if (isStudent) {
                            if (isBook) {
                                students.get(edits[0]).getBooks_borrows().add(newb);
                            }
                            else{
                                students.get(edits[0]).getThesis_borrows().add(newb);
                            }
                            students.get(edits[0]).setStudent_borrows(students.get(edits[0]).getStudent_borrows() + 1);
                        }     
                        else{
                            if (isBook) {
                                staffs.get(edits[0]).getBooks_borrows().add(newb);
                            }
                            else{
                                staffs.get(edits[0]).getThesis_borrows().add(newb);
                            }
                            staffs.get(edits[0]).setStaff_borrows(staffs.get(edits[0]).getStaff_borrows() + 1);
                        }
                        System.out.println("success");
                    }                    
                }


                //return                // EX : return#11037845|MyPass|L001|B001|2023-04-15|18:30
                else if (s1.compareTo("return") == 0)
                {   
                    //end date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date datep = null;
                    try {
                        datep = dateFormat.parse(edits[4] + " " + edits[5]);
                    } catch (Exception e) {                       
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                    Borrow retb = null;
                    boolean let = true , isStudent = true , isBook = true;
                    //check person 
                    if (let) {
                        if (staffs.containsKey(edits[0])) {
                            isStudent = false;
                        }
                        else if (!students.containsKey(edits[0])) {
                            let = false;
                            System.out.println("not-found");
                        }   
                    }
                    
                    //check password
                    if (let) {
                        if (isStudent && students.get(edits[0]).get_student_password().compareTo(edits[1]) != 0) {
                            let = false;
                            System.out.println("invalid-pass");
                        }
                        else if (!isStudent && staffs.get(edits[0]).get_staff_password().compareTo(edits[1]) != 0) {
                            let = false;
                            System.out.println("invalid-pass");
                        }
                    }
                    
                    //check library and thing
                    if (let) {
                        if (libraries.containsKey(edits[2])) 
                        {
                            if (libraries.get(edits[2]).getthesis().containsKey(edits[3])) 
                            {
                                isBook = false;
                            }
                            else if (!libraries.get(edits[2]).getBooks().containsKey(edits[3])) 
                            {
                                let = false;
                                System.out.println("not-found");    
                            }    
                        }
                        else
                        {
                            let = false;
                            System.out.println("not-found");
                        }  
                    }

                    //check borrow
                    int reti = -5;
                    if (let) {
                        let = false;
                        if (isStudent && isBook) {
                            ArrayList<Borrow> tmp = students.get(edits[0]).getBooks_borrows(); 
                            for (int index = 0; index < tmp.size(); index++) {
                                if ((retb == null || tmp.get(index).getDate().after(retb.getDate())) && (tmp.get(index).getUserid().compareTo(edits[0]) == 0) && (tmp.get(index).getThingid().compareTo(edits[3]) == 0) && (tmp.get(index).getLibraryid().compareTo(edits[2]) == 0)) {
                                    let = true;
                                    retb = tmp.get(index);
                                    reti = index;
                                }
                            }
                        }
                        else if (isStudent) {
                            ArrayList<Borrow> tmp = students.get(edits[0]).getThesis_borrows(); 
                            for (int index = 0; index < tmp.size(); index++) {
                                if ((retb == null || tmp.get(index).getDate().after(retb.getDate())) &&(tmp.get(index).getUserid().compareTo(edits[0]) == 0) && (tmp.get(index).getThingid().compareTo(edits[3]) == 0) && (tmp.get(index).getLibraryid().compareTo(edits[2]) == 0)) {
                                    let = true;
                                    reti = index;
                                    retb = tmp.get(index);
                                }
                            }
                        }
                        else if (isBook) {
                            ArrayList<Borrow> tmp = staffs.get(edits[0]).getBooks_borrows(); 
                            for (int index = 0; index < tmp.size(); index++) {
                                if ((retb == null || tmp.get(index).getDate().after(retb.getDate())) &&(tmp.get(index).getUserid().compareTo(edits[0]) == 0) && (tmp.get(index).getThingid().compareTo(edits[3]) == 0) && (tmp.get(index).getLibraryid().compareTo(edits[2]) == 0)) {
                                    let = true;
                                    reti = index;
                                    retb = tmp.get(index);
                                }
                            }
                        }
                        else{
                            ArrayList<Borrow> tmp = staffs.get(edits[0]).getThesis_borrows(); 
                            for (int index = 0; index < tmp.size(); index++) {
                                if ((retb == null || tmp.get(index).getDate().after(retb.getDate())) &&(tmp.get(index).getUserid().compareTo(edits[0]) == 0) && (tmp.get(index).getThingid().compareTo(edits[3]) == 0) && (tmp.get(index).getLibraryid().compareTo(edits[2]) == 0)) {
                                    let = true;
                                    reti = index;
                                    retb = tmp.get(index);
                                }
                            }
                        }
                        
                        if (reti == -5) {
                            let = false;
                            System.out.println("not-found");
                        }
                    }

                    if (let) {
                        if (isStudent) {
                            Student person = students.get(edits[0]);
                            int jarime = retb.returndebt(retb.getDate() , datep);
                            person.setStudent_borrows(person.getStudent_borrows() - 1);
                            if (isBook) {
                                Book book = libraries.get(edits[2]).getBooks().get(edits[3]);
                                book.setBook_borrows(book.getBook_borrows() - 1);
                                person.getBooks_borrows().remove(reti);
                            }
                            else
                            {
                                libraries.get(edits[2]).getthesis().get(edits[3]).setIsborrowed(false);
                                person.getThesis_borrows().remove(reti);
                            }

                            //jarime
                            if (jarime > 0) {
                                person.setStudent_debt(person.getStudent_debt() + jarime);
                                System.out.println(jarime);
                            }
                            else
                            {
                                System.out.println("success");
                            }
                        }
                        else{
                            Staff person = staffs.get(edits[0]);
                            int jarime = retb.returndebt(retb.getDate() , datep);
                            person.setStaff_borrows(person.getStaff_borrows() - 1);
                            if (isBook) {
                                Book book = libraries.get(edits[2]).getBooks().get(edits[3]);
                                book.setBook_borrows(book.getBook_borrows() - 1);
                                person.getBooks_borrows().remove(reti);
                            }
                            else
                            {
                                libraries.get(edits[2]).getthesis().get(edits[3]).setIsborrowed(false);
                                person.getThesis_borrows().remove(reti);
                            }
                            if (jarime > 0) {
                                person.setStaff_debt(person.getStaff_debt() + jarime);
                                System.out.println(jarime);
                            }
                            else
                            {
                                System.out.println("success");
                            }
                        }
                    } 
                }
                

                //search                 //EX : search#Programming
                else if (s1.compareTo("search") == 0) {
                    ArrayList<String> ids = new ArrayList<>(); 
                    //check books
                    for(Library i : libraries.values()){
                        for(Book j : i.getBooks().values()){
                            if (j.getBook_name().toLowerCase().contains(edits[0].toLowerCase()) || j.getBook_entesharat().toLowerCase().contains(edits[0].toLowerCase()) || j.getBook_writer().toLowerCase().contains(edits[0].toLowerCase())) {
                                if (!ids.contains(j.getBook_id())) {
                                    ids.add(j.getBook_id());    
                                }
                                
                            }
                        }
                        for(Thesis j : i.getthesis().values()){
                            if (j.getThesis_name().toLowerCase().contains(edits[0].toLowerCase()) || j.getThesis_ostad().toLowerCase().contains(edits[0].toLowerCase()) || j.getThesis_student().toLowerCase().contains(edits[0].toLowerCase())) {
                                if (!ids.contains(j.getThesis_id())) {
                                    ids.add(j.getThesis_id());    
                                }
                            }
                        }
                    }
                    if (ids.size() == 0) {
                        System.out.println("not-found");
                    }
                    else{
                        ids.sort(null);
                        for (int i = 0; i < ids.size(); i++) {
                            if (i!=0) {
                                System.out.print("|");
                            }
                            System.out.print(ids.get(i));
                        }
                        System.out.print("\n");
                    }
                }

                //search user
                else if (s1.compareTo("search-user") == 0) {
                    ArrayList<String> ids = new ArrayList<>();
                    if (staffs.containsKey(edits[0])) {
                        if (staffs.get(edits[0]).get_staff_password().compareTo(edits[1]) == 0) {
                            for(Staff i : staffs.values()){
                                if (i.get_staff_name().toLowerCase().contains(edits[2].toLowerCase()) || i.get_staff_family().toLowerCase().contains(edits[2].toLowerCase())) {
                                    ids.add(i.get_staff_id());
                                }
                            }
                            for(Student i : students.values()){
                                if (i.get_student_name().toLowerCase().contains(edits[2].toLowerCase()) || i.get_student_family().toLowerCase().contains(edits[2].toLowerCase())) {
                                    ids.add(i.get_student_id());
                                }
                            }
                            if (ids.size() == 0) {
                                System.out.println("not-found");
                            }
                            else{
                                ids.sort(null);
                                for (int i = 0; i < ids.size(); i++) {
                                    if (i!=0) {
                                        System.out.print("|");
                                    }
                                    System.out.print(ids.get(i));
                                }
                                System.out.print("\n");
                            }
                        }
                        else{
                            System.out.println("invalid-pass");
                        }
                    }
                    else if (students.containsKey(edits[0])) {
                        if (students.get(edits[0]).get_student_password().compareTo(edits[1]) == 0) {
                            for(Staff i : staffs.values()){
                                if (i.get_staff_name().toLowerCase().contains(edits[2].toLowerCase()) || i.get_staff_family().toLowerCase().contains(edits[2].toLowerCase())) {
                                    ids.add(i.get_staff_id());
                                }
                            }
                            for(Student i : students.values()){
                                if (i.get_student_name().toLowerCase().contains(edits[2].toLowerCase()) || i.get_student_family().toLowerCase().contains(edits[2].toLowerCase())) {
                                    ids.add(i.get_student_id());
                                }
                            }
                            if (ids.size() == 0) {
                                System.out.println("not-found");
                            }
                            else{
                                ids.sort(null);
                                for (int i = 0; i < ids.size(); i++) {
                                    if (i!=0) {
                                        System.out.print("|");
                                    }
                                    System.out.print(ids.get(i));
                                }
                                System.out.print("\n");
                            }
                        }
                        else{
                            System.out.println("invalid-pass");
                        }
                    }
                    else{
                        System.out.println("not-found");
                    }
                    
                }
                
                //category-report
                else if (s1.compareTo("category-report") == 0) {
                    int bks = 0 , ths = 0;
                    if (categories.containsKey(edits[0]) || edits[0].compareTo("null") == 0) {
                        for(Library i : libraries.values()){
                            for(Book j : i.getBooks().values()){
                                if (j.getBook_category().toLowerCase().contains(edits[0].toLowerCase())) {
                                    bks += j.getBook_tedad_copy();
                                }
                            }
                            for(Thesis j : i.getthesis().values()){
                                if (j.getThesis_category().toLowerCase().contains(edits[0].toLowerCase())) {
                                    ths++;
                                }
                            }
                        }
                        System.out.println(bks + " " + ths);
                    }
                    else{
                        System.out.println("not-found");
                    }
                }

                //library-report
                else if (s1.compareTo("library-report") == 0) {
                    if (libraries.containsKey(edits[0])) {
                        int bks = 0, ths = 0 , bkbrs = 0 , thbrs = 0;
                        for(Book j : libraries.get(edits[0]).getBooks().values()){
                            bks += j.getBook_tedad_copy();
                        }
                        ths += libraries.get(edits[0]).getthesis().size();
                        for(Staff i : staffs.values()){
                            for(Borrow j : i.getBooks_borrows())
                            {
                                if (j.getLibraryid().compareTo(edits[0]) == 0) {
                                    bkbrs += 1;
                                }
                            }
                            for(Borrow j : i.getThesis_borrows())
                            {   
                                if (j.getLibraryid().compareTo(edits[0]) == 0) {
                                    thbrs += 1;
                                }
                            }
                        }
                        for(Student i : students.values()){
                            for(Borrow j : i.getBooks_borrows())
                            {
                                if (j.getLibraryid().compareTo(edits[0]) == 0) {
                                    bkbrs += 1;
                                }
                            }
                            for(Borrow j : i.getThesis_borrows())
                            {
                                if (j.getLibraryid().compareTo(edits[0]) == 0) {
                                    thbrs += 1;
                                }
                            }
                            
                        }
                    
                        System.out.println(bks + " "  + ths + " " + bkbrs + " " + thbrs);
                    }
                    else{
                        System.out.println("not-found");
                    }
                }

                //report-passed-deadline
                else if (s1.compareTo("report-passed-deadline") == 0) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date datep = null;
                    try {
                        datep = dateFormat.parse(edits[1] + " " + edits[2]);
                    } catch (Exception e) {                       
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                    if (libraries.containsKey(edits[0])) {
                        ArrayList<String> res = new ArrayList<>();
                        for(Staff i : staffs.values()){
                            for (Borrow j : i.getBooks_borrows()) {
                                if (j.getLibraryid().compareTo(edits[0]) == 0 && j.returndebt(j.getDate(), datep) != 0) {
                                    if (!res.contains(j.getThingid())) {
                                        res.add(j.getThingid());
                                    }
                                }
                            }
                            for (Borrow j : i.getThesis_borrows()) {
                                if (j.getLibraryid().compareTo(edits[0]) == 0 && j.returndebt(j.getDate(), datep) != 0) {
                                    if (!res.contains(j.getThingid())) {
                                        res.add(j.getThingid());
                                    }
                                }
                            }
                        }
                        for(Student i : students.values()){
                            for (Borrow j : i.getBooks_borrows()) {
                                if (j.getLibraryid().compareTo(edits[0]) == 0 && j.returndebt(j.getDate(), datep) != 0) {
                                    if (!res.contains(j.getThingid())) {
                                        res.add(j.getThingid());
                                    }
                                }
                            }
                            for (Borrow j : i.getThesis_borrows()) {
                                if (j.getLibraryid().compareTo(edits[0]) == 0 && j.returndebt(j.getDate(), datep) != 0) {
                                    if (!res.contains(j.getThingid())) {
                                        res.add(j.getThingid());
                                    }
                                }
                            }
                        }
                        if (res.size() == 0) {
                            System.out.println("none");
                        }
                        else{
                            res.sort(null);
                            for (int i = 0; i < res.size(); i++) {
                                if (i!=0) {
                                    System.out.print("|");
                                }
                                System.out.print(res.get(i));
                            }
                            System.out.print("\n");
                        }
                    }
                    else{
                        System.out.println("not-found");
                    }
                }
            
                //reserve-seat
                else if (s1.compareTo("reserve-seat") == 0) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date = null;
                    try {
                        date = dateFormat.parse(edits[3] + " " + edits[4]);
                    } catch (Exception e) {
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                    Date date2 = null;
                    try {
                        date2 = dateFormat.parse(edits[3] + " " + edits[5]);
                    } catch (Exception e) {
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date rooz = null;
                    try {
                        rooz = dateFormat2.parse(edits[3] + " " + edits[4]);
                    } catch (Exception e) {
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                    boolean isStudent = true , let = true;
                    //check person and password
                    if (let) {
                        if (staffs.containsKey(edits[0])) {
                            isStudent = false;
                        }
                        else if (!students.containsKey(edits[0])) {
                            let = false;
                            System.out.println("not-found");
                        }   
                    }
                    
                    //check password
                    if (let) {
                        if (isStudent && students.get(edits[0]).get_student_password().compareTo(edits[1]) != 0) {
                            let = false;
                            System.out.println("invalid-pass");
                        }
                        else if (!isStudent && staffs.get(edits[0]).get_staff_password().compareTo(edits[1]) != 0) {
                            let = false;
                            System.out.println("invalid-pass");
                        }
                    }
                    
                    if (let) {
                        if (isStudent && students.get(edits[0]).getResdates().contains(rooz)) {
                            let = false;
                            System.out.println("not-allowed");
                        }
                        else if (!isStudent && staffs.get(edits[0]).getResdates().contains(rooz)) {
                            let = false;
                            System.out.println("not-allowed");
                        }
                    }

                    //check library
                    if (let && !libraries.containsKey(edits[2])) 
                    {
                        let = false;
                        System.out.println("not-found");        
                    }

                    
                    seat tmpp = new seat(date,date2);
                    if (let) {
                        if (!tmpp.hours(date, date2)) {
                            System.out.println("not-allowed");
                            let = false;
                        }
                    }

                    if (let) {
                        for(seat i : libraries.get(edits[2]).getseats().values()){
                            if (tmpp.getStart().before(i.getEnd())&& tmpp.getEnd().after(i.getStart())) {
                                System.out.println("not-allowed");
                                let = false;
                                break;
                            }
                        }
                    }

                    if (let && libraries.get(edits[2]).getResday().containsKey(rooz) && libraries.get(edits[2]).getResday().get(rooz) == libraries.get(edits[2]).get_library_tedad_miz()) {
                        System.out.println("not-available");
                        let = false;
                    }

                    if(let){
                        libraries.get(edits[2]).getseats().put(edits[0],tmpp);
                        if (libraries.get(edits[2]).getResday().containsKey(rooz)) {
                            libraries.get(edits[2]).getResday().put(rooz, libraries.get(edits[2]).getResday().get(rooz)+1);
                        }
                        else{
                            libraries.get(edits[2]).getResday().put(rooz,1);
                        }
                        if (isStudent) {
                            students.get(edits[0]).getResdates().add(rooz);
                        }
                        else{
                            staffs.get(edits[0]).getResdates().add(rooz);
                        }
                        libraries.get(edits[2]).setReserves(libraries.get(edits[2]).getReserves() + 1);
                        System.out.println("success");
                    }
                    
                }
            }
        }
        scanner.close();
    }
}

class seat{
    private Date start;
    private Date end;

    public Date getStart() {
        return this.start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
    public seat(Date start , Date end){
        this.start = start;
        this.end = end;
    }
    public boolean hours(Date start , Date end){
        long tmp = end.getTime() - start.getTime();
        if (tmp > 8*3600000L) {
            return false;
        }
        return true;
    }
}
class Book 
{
    //fields
    private String book_id;
    private String book_name;
    private String book_writer;
    private String book_entesharat;
    private int book_sal_chap;
    private int book_tedad_copy;
    private String book_category;
    private String book_library;
    private int book_borrows;

    public int getBook_borrows() {
        return this.book_borrows;
    }

    public void setBook_borrows(int book_borrows) {
        this.book_borrows = book_borrows;
    }

    public String getBook_id() {
        return this.book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return this.book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_writer() {
        return this.book_writer;
    }

    public void setBook_writer(String book_writer) {
        this.book_writer = book_writer;
    }

    public String getBook_entesharat() {
        return this.book_entesharat;
    }

    public void setBook_entesharat(String book_entesharat) {
        this.book_entesharat = book_entesharat;
    }

    public int getBook_sal_chap() {
        return this.book_sal_chap;
    }

    public void setBook_sal_chap(int book_sal_chap) {
        this.book_sal_chap = book_sal_chap;
    }

    public int getBook_tedad_copy() {
        return this.book_tedad_copy;
    }

    public void setBook_tedad_copy(int book_tedad_copy) {
        this.book_tedad_copy = book_tedad_copy;
    }

    public String getBook_category() {
        return this.book_category;
    }

    public void setBook_category(String book_category) {
        this.book_category = book_category;
    }

    public String getBook_library() {
        return this.book_library;
    }

    public void setBook_library(String book_library) {
        this.book_library = book_library;
    }

    //constractors
    public Book(String book_id , String book_name , String book_writer , String book_entesharat , int book_sal_chap , int book_tedad_copy , String book_category , String book_library)
    {
        this.book_id = book_id;
        this.book_category = book_category;
        this.book_entesharat = book_entesharat;
        this.book_library = book_library;
        this.book_name = book_name;
        this.book_sal_chap = book_sal_chap;
        this.book_tedad_copy = book_tedad_copy;
        this.book_writer = book_writer;
        book_borrows = 0;
    }    
}

class Library 
{
    private HashMap<String, Book> books;
    private HashMap<String, Thesis> thesis;
    private HashMap<String, seat> seats;
    private HashMap<Date, Integer> resday;

    public HashMap<Date, Integer> getResday() {
        return this.resday;
    }

    public void setResday(HashMap<Date, Integer> resday) {
        this.resday = resday;
    }

    public HashMap<String, seat> getseats() {
        return this.seats;
    }

    public void setseats(HashMap<String, seat> seats) {
        this.seats = seats;
    }

    private String library_id;
    private String library_name;
    private int library_tedad_miz;
    private int library_sal_tasis;
    private String library_address;
    private int reserves;

    public int getReserves() {
        return this.reserves;
    }
    
    public void setReserves(int reserves) {
        this.reserves = reserves;
    }
    
    //constractors
    public Library(String library_id , String library_name ,  int library_sal_tasis , int library_tedad_miz , String library_address)
    {
        this.library_id = library_id;
        this.library_address = library_address;
        this.library_name = library_name;
        this.library_sal_tasis = library_sal_tasis;
        this.library_tedad_miz = library_tedad_miz;
        this.reserves = 0;
        books = new HashMap<>();
        thesis = new HashMap<>();
        resday = new HashMap<>();
        seats = new HashMap<>();
    }    

   
    //getters
    public HashMap<String,Thesis> getthesis()
    {
        return this.thesis;
    }
    public HashMap<String, Book> getBooks() {
        return this.books;
    }
    public String get_library_id()
    {
        return this.library_id;
    }
    public String get_library_name()
    {
        return this.library_name;
    }
    public int get_library_tedad_miz()
    {
        return this.library_tedad_miz;
    }
    public int get_library_sal_tasis()
    {
        return this.library_sal_tasis;
    }
    public String get_library_address()
    {
        return this.library_address;
    }   
    
    //setters
    public void setThesis(HashMap<String,Thesis> thesis)
    {
        this.thesis = thesis;
    }
    public void set_library_id(String library_id)
    {
        this.library_id = library_id;
    }
    public void setBooks(HashMap<String, Book> books) {
        this.books = books;   
    }
    public void set_library_tedad_miz(int library_tedad_miz)
    {
        this.library_tedad_miz = library_tedad_miz;
    }
    public void set_library_name(String library_name)
    {
        this.library_name = library_name;
    }
    public void set_library_sal_tasis(int library_sal_tasis)
    {
        this.library_sal_tasis = library_sal_tasis;
    }
    public void set_library_address(String library_address)
    {
        this.library_address = library_address;
    }   
}

class Staff 
{
    //fields
    private String staff_id;
    private String staff_name;
    private String staff_password;
    private String staff_family;
    private String staff_kod_meli;
    private int staff_sal_tavalod;
    private String staff_address;
    private int staff_borrows;
    private int staff_debt;
    ArrayList<Borrow> books_borrows;
    ArrayList<Borrow> thesis_borrows;
    ArrayList<Date> resdates;

    public ArrayList<Date> getResdates() {
        return this.resdates;
    }

    public void setResdates(ArrayList<Date> resdates) {
        this.resdates = resdates;
    }

    private boolean rese;

    public boolean isRese() {
        return this.rese;
    }

    public void setRese(boolean rese) {
        this.rese = rese;
    }

    public ArrayList<Borrow> getBooks_borrows() {
        return this.books_borrows;
    }

    public void setBooks_borrows(ArrayList<Borrow> books_borrows) {
        this.books_borrows = books_borrows;
    }

    public ArrayList<Borrow> getThesis_borrows() {
        return this.thesis_borrows;
    }

    public void setThesis_borrows(ArrayList<Borrow> thesis_borrows) {
        this.thesis_borrows = thesis_borrows;
    }

    public int getStaff_debt() {
        return this.staff_debt;
    }

    public void setStaff_debt(int staff_debt) {
        this.staff_debt = staff_debt;
    }

    public int getStaff_borrows() {
        return this.staff_borrows;
    }
    public void setStaff_borrows(int staff_borrows) {
        this.staff_borrows = staff_borrows;
    }

    //constractors
    public Staff(String staff_id , String staff_name , String staff_password , String staff_family , String staff_kod_meli , int staff_sal_tavalod , String staff_address)
    {
        this.staff_id = staff_id;
        this.staff_address = staff_address;
        this.staff_family = staff_family;
        this.staff_name = staff_name;
        this.staff_kod_meli = staff_kod_meli;
        this.staff_sal_tavalod = staff_sal_tavalod;
        this.staff_password = staff_password;
        staff_debt = 0;
        staff_borrows = 0;
        books_borrows = new ArrayList<>();
        thesis_borrows = new ArrayList<>();
        rese = false;
        resdates = new ArrayList<>();
    }    

    //getters
    public String get_staff_id()
    {
        return this.staff_id;
    }
    public String get_staff_name()
    {
        return this.staff_name;
    }
    public String get_staff_password()
    {
        return this.staff_password;
    }
    public String get_staff_family()
    {
        return this.staff_family;
    }
    public int get_staff_sal_tavalod()
    {
        return this.staff_sal_tavalod;
    }
    public String get_staff_kod_meli()
    {
        return this.staff_kod_meli;
    }
    public String get_staff_address()
    {
        return this.staff_address;
    }   

    //setters
    public void set_staff_id(String staff_id)
    {
        this.staff_id = staff_id;
    }
    public void set_staff_password(String staff_password)
    {
        this.staff_password = staff_password;
    }
    public void set_staff_name(String staff_name)
    {
        this.staff_name = staff_name;
    }
    public void set_staff_family(String staff_family)
    {
        this.staff_family = staff_family;
    }
    public void set_staff_kod_meli(String staff_kod_meli)
    {
        this.staff_kod_meli = staff_kod_meli;
    }
    public void set_staff_sal_tavalod(int staff_sal_tavalod)
    {
        this.staff_sal_tavalod = staff_sal_tavalod;
    }
    public void set_staff_address(String staff_address)
    {
        this.staff_address = staff_address;
    }
}

class Student 
{
    //fields
    private String student_id;
    private String student_name;
    private String student_password;
    private String student_family;
    private String student_kod_meli;
    private int student_sal_tavalod;
    private String student_address;
    private int student_borrows;
    private int student_debt;
    ArrayList<Borrow> books_borrows;
    ArrayList<Borrow> thesis_borrows;
    private boolean rese;
    ArrayList<Date> resdates;

    public ArrayList<Date> getResdates() {
        return this.resdates;
    }

    public void setResdates(ArrayList<Date> resdates) {
        this.resdates = resdates;
    }

    public boolean isRese() {
        return this.rese;
    }

    public void setRese(boolean rese) {
        this.rese = rese;
    }

    public ArrayList<Borrow> getBooks_borrows() {
        return this.books_borrows;
    }

    public void setBooks_borrows(ArrayList<Borrow> books_borrows) {
        this.books_borrows = books_borrows;
    }

    public ArrayList<Borrow> getThesis_borrows() {
        return this.thesis_borrows;
    }

    public void setThesis_borrows(ArrayList<Borrow> thesis_borrows) {
        this.thesis_borrows = thesis_borrows;
    }

    public int getStudent_debt() {
        return this.student_debt;
    }

    public void setStudent_debt(int student_debt) {
        this.student_debt = student_debt;
    }

    public int getStudent_borrows() {
        return this.student_borrows;
    }
    public void setStudent_borrows(int student_borrows) {
        this.student_borrows = student_borrows;
    }
    
    //constractors
    public Student(String student_id , String student_password, String student_name  , String student_family , String student_kod_meli , int student_sal_tavalod , String student_address)
    {
        this.student_id = student_id;
        this.student_address = student_address;
        this.student_family = student_family;
        this.student_name = student_name;
        this.student_kod_meli = student_kod_meli;
        this.student_sal_tavalod = student_sal_tavalod;
        this.student_password = student_password;
        student_borrows = 0;
        student_debt = 0;
        books_borrows = new ArrayList<>();
        thesis_borrows = new ArrayList<>();
        rese = false;
        resdates = new ArrayList<>();
    }    

    //getters
    public String get_student_id()
    {
        return this.student_id;
    }
    public String get_student_name()
    {
        return this.student_name;
    }
    public String get_student_password()
    {
        return this.student_password;
    }
    public String get_student_family()
    {
        return this.student_family;
    }
    public int get_student_sal_tavalod()
    {
        return this.student_sal_tavalod;
    }
    public String get_student_kod_meli()
    {
        return this.student_kod_meli;
    }
    public String get_student_address()
    {
        return this.student_address;
    }   

    //setters
    public void set_student_id(String student_id)
    {
        this.student_id = student_id;
    }
    public void set_student_password(String student_password)
    {
        this.student_password = student_password;
    }
    public void set_student_name(String student_name)
    {
        this.student_name = student_name;
    }
    public void set_student_family(String student_family)
    {
        this.student_family = student_family;
    }
    public void set_student_kod_meli(String student_kod_meli)
    {
        this.student_kod_meli = student_kod_meli;
    }
    public void set_student_sal_tavalod(int student_sal_tavalod)
    {
        this.student_sal_tavalod = student_sal_tavalod;
    }
    public void set_student_address(String student_address)
    {
        this.student_address = student_address;
    }
}

class Thesis 
{
    //fields
    private String thesis_id;
    private String thesis_name;
    private String thesis_student;
    private String thesis_ostad;
    private int thesis_sal;
    private String thesis_category;
    private String thesis_library;
    private boolean isborrowed;

    public boolean getisborrowed() {
        return this.isborrowed;
    }
    public void setIsborrowed(boolean isborrowed) {
        this.isborrowed = isborrowed;
    }
    public String getThesis_id() {
        return this.thesis_id;
    }
    public void setThesis_id(String thesis_id) {
        this.thesis_id = thesis_id;
    }
    public String getThesis_name() {
        return this.thesis_name;
    }
    public void setThesis_name(String thesis_name) {
        this.thesis_name = thesis_name;
    }
    public String getThesis_student() {
        return this.thesis_student;
    }
    public void setThesis_student(String thesis_student) {
        this.thesis_student = thesis_student;
    }
    public String getThesis_ostad() {
        return this.thesis_ostad;
    }  
    public void setThesis_ostad(String thesis_ostad) {
        this.thesis_ostad = thesis_ostad;
    }
    public int getThesis_sal() {
        return this.thesis_sal;
    }
    public void setThesis_sal(int thesis_sal) {
        this.thesis_sal = thesis_sal;
    }
    public String getThesis_category() {
        return this.thesis_category;
    }
    public void setThesis_category(String thesis_category) {
        this.thesis_category = thesis_category;
    }
    public String getThesis_library() {
        return this.thesis_library;
    }
    public void setThesis_library(String thesis_library) {
        this.thesis_library = thesis_library;
    }
    //constractors
    public Thesis(String thesis_id , String thesis_name , String thesis_student , String thesis_ostad , int thesis_sal , String thesis_category , String thesis_library)
    {
        this.thesis_id = thesis_id;
        this.thesis_category = thesis_category;
        this.thesis_ostad = thesis_ostad;
        this.thesis_library = thesis_library;
        this.thesis_name = thesis_name;
        this.thesis_sal = thesis_sal;
        this.thesis_student = thesis_student;
        isborrowed = false;
    }    
}
 
class Category 
{
    //fields
    private String category_id;
    private String category_name;
    public Category(String category_id , String category_name)
    {
        this.category_id = category_id;
        this.category_name = category_name;
    }
    //getters
    public String get_category_id()
    {
        return this.category_id;
    }
    public String get_category_name()
    {
        return this.category_name;
    }
    //setters
    public void set_category_id(String category_id)
    {
        this.category_id = category_id;
    }
    public void set_category_name(String category_name)
    {
        this.category_name = category_name;
    }
}

class Borrow
{
    private Date date;
    private String userid;
    private String thingid;
    private String password;
    private String libraryid;
    private boolean isStudent;
    private boolean isBook;

    public Borrow(Date date , String userid , String thingid , String password , String libraryid)
    {
        this.date = date;
        this.thingid = thingid;
        this.userid = userid;
        this.password = password;
        this.libraryid = libraryid;
    }
    
    public int returndebt(Date start , Date end)
    {
        long first = start.getTime();
        long last = end.getTime();
        int time = (int)((last - first) / 3600000);
        int tmp = (int)((last - first) % 3600000L);
        if (tmp >= 1800000) {
            time++;
        }
        // System.out.println(start.toString() + "   &&   " + end.toString());
        //
        if (isStudent) {
            if (isBook) { //10 days -> 240hours
                if (time < 240) {
                    return 0;
                }
                else
                return (time - 240)*50;
            }
            else{ //thesis -> 7 days -> 7*24 = 168 hours
                if (time < 168) {
                    return 0;
                }
                else
                return (time - 168)*50;
            }
        }
        else {
            if (isBook) { //14 days -> 336hours
                if (time < 336) {
                    return 0;
                }
                else
                return (time - 336)*100;
            }
            else{ //thesis -> 10 days -> 10*24 = 240 hours
                if (time < 240) {
                    return 0;
                }
                else
                return (time - 240)*100;
            }
        }

    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getUserid() {
        return this.userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getThingid() {
        return this.thingid;
    }
    public void setThingid(String thingid) {
        this.thingid = thingid;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getLibraryid() {
        return this.libraryid;
    }
    public void setLibraryid(String libraryid) {
        this.libraryid = libraryid;
    }
    public boolean isIsStudent() {
        return this.isStudent;
    }
    public void setIsStudent(boolean isStudent) {
        this.isStudent = isStudent;
    }
    public boolean isIsBook() {
        return this.isBook;
    }
    public void setIsBook(boolean isBook) {
        this.isBook = isBook;
    }
}
