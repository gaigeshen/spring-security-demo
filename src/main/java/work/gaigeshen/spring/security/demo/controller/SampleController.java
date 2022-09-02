package work.gaigeshen.spring.security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import work.gaigeshen.spring.security.demo.commons.web.Result;
import work.gaigeshen.spring.security.demo.commons.web.Results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author gaigeshen
 */
@RequestMapping("/samples")
@RestController
public class SampleController {

    @PreAuthorize("hasAuthority('users:create')")
    @PostMapping("/create")
    public Result<?> createSample(@RequestBody SampleDTO sampleDTO) {
        return Results.create(sampleDTO);
    }

    @PreAuthorize("hasAuthority('users:list')")
    @GetMapping("/list")
    public Result<Collection<SampleDTO>> listSamples() {
        Collection<SampleDTO> samples = new ArrayList<>();
        samples.add(new SampleDTO("jack", "123", Collections.singleton("users:read")));
        samples.add(new SampleDTO("tom", "123", Collections.singleton("users:create")));
        return Results.create(samples);
    }
}
