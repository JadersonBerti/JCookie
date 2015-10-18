package com.extremekillers.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.extremekillers.entity.SerialPlayer;

@FacesConverter(value = "serialPlayerConverter")
public class SerialPlayerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
        if (value != null && !value.equals("")) {

            Integer id = new Integer(value);
            // Quando tivermos "conversando" com o banco de dados, faremos o seguinte por ex:
            // PessoaDAO pessoaDAO = new PessoaDAO();
            // Pessoa pessoa = pessoaDAO.find(id);
            SerialPlayer serialPlayer = new SerialPlayer();
            serialPlayer.setId(id);

            return serialPlayer;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        if (value instanceof SerialPlayer) {
        	SerialPlayer serialPlayer = (SerialPlayer) value;
            return String.valueOf(serialPlayer.getId());
        }
        return "";
    }

}
