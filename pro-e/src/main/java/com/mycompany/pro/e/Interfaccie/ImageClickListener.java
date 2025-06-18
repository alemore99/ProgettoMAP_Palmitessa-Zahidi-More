package com.mycompany.pro.e.Interfaccie;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aless
 */
//Si occupa di prendere le cordinate da un click
@FunctionalInterface
public interface ImageClickListener {

    /**
     *
     * @param x
     * @param y
     */
    void onImageClick(int x, int y);
}
