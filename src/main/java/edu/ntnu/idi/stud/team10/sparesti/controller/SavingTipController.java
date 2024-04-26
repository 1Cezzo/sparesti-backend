package edu.ntnu.idi.stud.team10.sparesti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ntnu.idi.stud.team10.sparesti.service.SavingTipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/savingtips")
@RestController
/** Simple controller for getting a random daily saving tip/fun factoid */
@Tag(name = "Saving Tips", description = "Operations related to getting a random daily saving tip")
public class SavingTipController {
  private final SavingTipService savingTipService;

  @Autowired
  public SavingTipController(SavingTipService savingTipService) {
    this.savingTipService = savingTipService;
  }

  @GetMapping("/get")
  @Operation(summary = "Get a random saving tip")
  public ResponseEntity<String> getDailySavingTip() {
    return ResponseEntity.ok().body(savingTipService.getRandomSavingTip());
  }
}
