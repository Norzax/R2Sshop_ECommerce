package com.aclass.r2sshop_ecommerce.controllers.ForAdmin;

import com.aclass.r2sshop_ecommerce.models.dto.PromoDTO;
import com.aclass.r2sshop_ecommerce.models.dto.RoleDTO;
import com.aclass.r2sshop_ecommerce.models.dto.common.ResponseDTO;
import com.aclass.r2sshop_ecommerce.services.promote.PromoteService;
import com.aclass.r2sshop_ecommerce.services.role.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "3. Admin: <Promote>")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/admin/promote")
public class PromoController {
    private final PromoteService promoteService;

    @Autowired
    public PromoController(PromoteService promoteService) {
        this.promoteService = promoteService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<PromoDTO>>> findAll() {
        ResponseDTO<List<PromoDTO>> response = promoteService.findAll();
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ResponseDTO<PromoDTO>> findById(@PathVariable Long id) {
        ResponseDTO<PromoDTO> response = promoteService.findById(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<PromoDTO>> create(@Valid @RequestBody PromoDTO promoDTO) {
        ResponseDTO<PromoDTO> response = promoteService.create(promoDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<PromoDTO>> update(@PathVariable Long id, @Valid @RequestBody PromoDTO updatedPromoDTO) {
        ResponseDTO<PromoDTO> response = promoteService.update(id, updatedPromoDTO);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id) {
        ResponseDTO<Void> response = promoteService.delete(id);
        HttpStatus httpStatus = response.getStatus().equals("404 NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(response);
    }
}
