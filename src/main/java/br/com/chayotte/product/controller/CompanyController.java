package br.com.chayotte.product.controller;

import br.com.chayotte.common.dto.ErrorResponse;
import br.com.chayotte.company.usecases.CompanyUseCases;
import br.com.chayotte.product.dto.company.CompanyCreateDto;
import br.com.chayotte.product.dto.company.CompanyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.net.URI.create;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyUseCases companyUseCases;

    @Operation(
            summary = "Register a new company",
            description = "Creates a new company in the system based on the provided data"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Company successfully created",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid company data provided",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
    })
    @PostMapping
    ResponseEntity<Void> registerCompany(@RequestBody @Valid CompanyCreateDto companyCreateDto) {
        var savedCompanyId = companyUseCases.registerNewCompany(companyCreateDto);
        return ResponseEntity.created(create(linkTo(methodOn(CompanyController.class)
                        .findCompanyById(savedCompanyId))
                        .toString()))
                .build();
    }

    @GetMapping("/{id}")
    ResponseEntity<CompanyResponseDto> findCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyUseCases.findCompanyById(id));
    }
}
