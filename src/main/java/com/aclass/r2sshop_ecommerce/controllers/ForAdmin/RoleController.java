package com.aclass.r2sshop_ecommerce.controllers.ForAdmin;

import com.aclass.r2sshop_ecommerce.models.dto.RoleDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.role.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "3. Admin: <Role>")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/admin/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<RoleDTO>>> findAll() {
        ResponseDTO<List<RoleDTO>> response = roleService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ResponseDTO<RoleDTO>> findById(@PathVariable Long id) {
        ResponseDTO<RoleDTO> response = roleService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<RoleDTO>> create(@Valid @RequestBody RoleDTO roleDTO) {
        ResponseDTO<RoleDTO> response = roleService.create(roleDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<RoleDTO>> update(@PathVariable Long id, @Valid @RequestBody RoleDTO updatedRoleDTO) {
        ResponseDTO<RoleDTO> response = roleService.update(id, updatedRoleDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = roleService.delete(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }
}
