package com.ureca.yoajungadmin.summary.controller;

import com.ureca.yoajungadmin.summary.service.PlanSummaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/plans/{id}/summary")
@RequiredArgsConstructor
public class PlanSummaryAdminController {

    private final PlanSummaryServiceImpl service;

    @PostMapping("/rebuild")
    public ResponseEntity<Void> rebuild(@PathVariable Long id) {
        service.rebuildSummary(id);
        return ResponseEntity.accepted().build();
    }
}

