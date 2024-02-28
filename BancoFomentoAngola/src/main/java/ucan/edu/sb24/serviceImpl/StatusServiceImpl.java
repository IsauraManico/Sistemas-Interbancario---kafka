/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.sb24.serviceImpl;

import java.util.List;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucan.edu.sb24.ententies.Status;
import ucan.edu.sb24.repository.StatusRepository;
import ucan.edu.sb24.service.StatusService;

/**
 *
 * @author jfk
 */
@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository StatusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.StatusRepository = statusRepository;
    }

    @Override
    public List<Status> getAllStatus() {
        return StatusRepository.findAll();
    }

    @Override
    public Status getStatusById(int id) {
        return StatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status not found with id: " + id));
    }

    @Override
    public Status createStatus(Status status) {
        // Lógica para criar um novo Status (por exemplo, validações podem ser adicionadas).
        return StatusRepository.save(status);
    }

    @Override
    public Status updateStatus(int id, Status status) {
        // Lógica para atualizar um Status existente (por exemplo, validações podem ser adicionadas).
        Status existingStatus = getStatusById(id);
        existingStatus.setDescricao(status.getDescricao());
        return StatusRepository.save(existingStatus);
    }

    @Override
    public void deleteStatus(int id) {
        // Lógica para excluir um Status (por exemplo, verifique se o Status existe antes de excluí-lo).
        Status Status = getStatusById(id);
        StatusRepository.delete(Status);
    }

}
