package demo.com.myemployees.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import demo.com.myemployees.pojo.Specialty;

public class Converter {
    @TypeConverter
    public String ListSpecialityToString(List<Specialty> specialties){
        return new Gson().toJson(specialties);
    }
    @TypeConverter
    public List<Specialty> stringSpecialityToList(String stringSpecialty){
        Gson gson=new Gson();
        ArrayList objects=gson.fromJson(stringSpecialty,ArrayList.class);
        ArrayList<Specialty> specialties=new ArrayList<>();
        for (Object o:objects){
            specialties.add(gson.fromJson(o.toString(),Specialty.class));
        }
        return specialties;
    }
}
