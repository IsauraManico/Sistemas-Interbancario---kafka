/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ucan.edu.sb24.service;

import java.util.List;
import ucan.edu.sb24.ententies.Status;

/**
 *
 * @author jfk
 */
public interface StatusService {

    List<Status> getAllStatus();

    Status getStatusById(int id);

    Status createStatus(Status status);

    Status updateStatus(int id, Status status);

    void deleteStatus(int id);
}
