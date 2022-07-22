package com.example.tallerredes.dtos;


import java.util.List;

public class EncuestaDto {

   public String Fullname;
   public String Datebirth;
   public String Address;
   public String PhoneNumber;
   public List<QuestionDto> Questions;
   public String AudioEncode;
   public int UserId;

   public class QuestionDto {
      String QuestionName;
      String Answer;
   }


}
