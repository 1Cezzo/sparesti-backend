package edu.ntnu.idi.stud.team10.sparesti.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for creating mock data. Not for use in production. */
@RestController
@RequestMapping("/api/mock")
@Tag(name = "Mock Data", description = "Operations relating to all mock data creation")
public class MockDataController {

}
