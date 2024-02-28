/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.emis.controller;

/**
 *
 * @author jfk
 */
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ucan.edu.emis.entidade.Status;
import ucan.edu.emis.service.StatusService;

@RestController
@RequestMapping("/sb24/-status")
public class StatusController {

    private final StatusService StatusService;

    @Autowired
    public StatusController(StatusService StatusService) {
        this.StatusService = StatusService;
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllsStatus() {
        List<Status> sStatus = StatusService.getAllStatus();
        return new ResponseEntity<>(sStatus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable int id) {
        Status Status = StatusService.getStatusById(id);
        return new ResponseEntity<>(Status, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status Status) {
        Status createdStatus = StatusService.createStatus(Status);
        return new ResponseEntity<>(createdStatus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable int id, @RequestBody Status Status) {
        Status updatedStatus = StatusService.updateStatus(id, Status);
        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }
    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable int id) {
        StatusService.deleteStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
}
