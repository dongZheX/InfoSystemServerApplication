package _import;

import javax.swing.JFileChooser;
public class MyFileFilter extends javax.swing.filechooser.FileFilter{
  public boolean accept(java.io.File f) {
    if (f.isDirectory())return true;
    return f.getName().endsWith(".xls");  //����Ϊѡ����.xlsΪ��׺���ļ�
  } 
  public String getDescription(){
    return ".xls";
  }
}