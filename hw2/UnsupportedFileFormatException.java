/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ce326.hw2;

/**
 *
 * @author dawg
 */
class UnsupportedFileFormatException extends Exception {
    
    static final long serialVersionUID = -4567891456L;
    
    UnsupportedFileFormatException() {
        super();
    }

    UnsupportedFileFormatException(String msg) {
	super(msg);
    }
}
