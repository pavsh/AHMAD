/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex1;

import java.io.Serializable;

/**
 *
 * @author pavel
 */
public class Category implements Serializable{
    String CategoryName;
    public Category(String name) {
        this.CategoryName = name;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String name) {
        this.CategoryName = name;
    }
    
    
}
