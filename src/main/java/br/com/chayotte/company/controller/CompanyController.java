package br.com.chayotte.company.controller;

import br.com.chayotte.common.dto.GenericErrorResponse;
import br.com.chayotte.common.dto.ValidationErrorResponse;
import br.com.chayotte.company.dto.company.CompanyCreateDto;
import br.com.chayotte.company.dto.company.CompanyResponseDto;
import br.com.chayotte.company.dto.company.CompanyUpdateDto;
import br.com.chayotte.company.usecases.CompanyUseCases;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
                            schema = @Schema(implementation = ValidationErrorResponse.class)
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

    @Operation(
            summary = "Find a company",
            description = "Fetch a company by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found a company with the queried ID",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CompanyResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Company not found with the specified ID",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GenericErrorResponse.class)
                    )
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<CompanyResponseDto> findCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyUseCases.findCompanyById(id));
    }

    @Operation(
            summary = "Update a company",
            description = "Updates an existing company based on the provided data"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Company successfully updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CompanyResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid company data provided",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Company not found with the specified ID",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GenericErrorResponse.class)
                    )
            )
    })
    @PutMapping("/{id}")
    ResponseEntity<CompanyResponseDto> updateCompany(@PathVariable Long id, @RequestBody @Valid CompanyUpdateDto companyUpdateDto) {
        var updatedCompany = companyUseCases.updateCompany(id, companyUpdateDto);
        return ResponseEntity.ok(updatedCompany);
    }

    @Operation(
            summary = "Delete a company",
            description = "Deletes a company by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Company successfully deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Company not found with the specified ID",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GenericErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyUseCases.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
