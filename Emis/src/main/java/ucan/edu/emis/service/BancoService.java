/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.service;

import java.util.List;
import ucan.edu.emis.entidade.Banco;

/**
 *
 * @author jfk
 */
public interface BancoService {

    List<Banco> getAllBancos();

    Banco getBancoById(Integer id);

    Banco saveBanco(Banco banco);

    void deleteBanco(Integer id);
}
