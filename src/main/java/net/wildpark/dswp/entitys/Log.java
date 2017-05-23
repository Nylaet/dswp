/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.entitys;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import net.wildpark.dswp.enums.LoggerLevel;


/**
 *
 * @author Panker-RDP
 * Класс логирования событий приложения.
 */
@Entity
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String text;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date added;
    private Exception errorType;
    private LoggerLevel loggerLevel;
    public Log() {
    }

    public Log(String text) {
        this.text = text;
        loggerLevel=LoggerLevel.INFO;
        added=new Date();
    }

    public Log(String text, LoggerLevel loggerLevel) {
        this.text = text;
        this.loggerLevel = loggerLevel;
        added=new Date();
    }

    public Log(String text, Exception exception, LoggerLevel loggerLevel) {
        this.text = text;
        this.errorType = exception;
        this.loggerLevel = loggerLevel;
        added=new Date();
    }

    public Log(Exception exception) {
        text=errorType.getMessage();
        this.errorType = exception;
        added=new Date();
    }
    
    

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return added;
    }

    public void setDate(Date date) {
        this.added = date;
    }

    public Exception getException() {
        return errorType;
    }
    
    public String getDateAsString(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yy HH:mm");
        if(added==null)return "Не известно";
        return dateFormat.format(added);
    }

    public void setException(Exception exception) {
        this.errorType = exception;
    }

    public LoggerLevel getLoggerLevel() {
        return loggerLevel;
    }

    public void setLoggerLevel(LoggerLevel loggerLevel) {
        this.loggerLevel = loggerLevel;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return text+" "+getDateAsString();
    }
    
}
