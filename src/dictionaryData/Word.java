/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionaryData;

import org.w3c.dom.Element;

/**
 *
 * @author VuQuang
 */
public class Word
{
    private String keyWord;
    private String meaning;
    
    public Word(String word, String mean){
        this.keyWord = word;
        this.meaning = mean;
    }
    
    public Word(Element eElement) {
               
        this.keyWord = eElement
          .getElementsByTagName("word")
          .item(0)
          .getTextContent();
        this.meaning = eElement
          .getElementsByTagName("meaning")
          .item(0)
          .getTextContent();
    }
    
    public void printWord() {
        System.out.println("word:" + this.keyWord);
        System.out.println("meaning:" + this.meaning);
        
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    
}
