package pl.gdynia.ctm.restctm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gdynia.ctm.restctm.model.ArrayWrapper;
import pl.gdynia.ctm.restctm.model.ArraysRepository;
import pl.gdynia.ctm.restctm.service.SortService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class SortController {
    private final SortService sortService;
    private final ArraysRepository arraysRepository;


    @Autowired
    public SortController(SortService sortService, ArraysRepository arraysRepository) {
        this.sortService = sortService;
        this.arraysRepository = arraysRepository;
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<CollectionModel<ArrayWrapper>> getSort() {
        var link = getMainLink();
        return new ResponseEntity<>(CollectionModel.of(arraysRepository.getArrays(), link), HttpStatus.OK);
    }

    @GetMapping(value = "/sort/{id}", produces = "application/vnd.ctm.v1+json")
    public ResponseEntity<EntityModel<ArrayWrapper>> getQuick(@PathVariable String id) {
        ArrayWrapper wrapper = arraysRepository.get(Integer.parseInt(id));
        sortService.sortInput("quick", wrapper);
        EntityModel<ArrayWrapper> arrayWrapperEntityModel = EntityModel.of(wrapper, getMainLink());
        return new ResponseEntity<>(arrayWrapperEntityModel, HttpStatus.OK);
    }

    @GetMapping(value = "/sort/{id}", produces = "application/vnd.ctm.v2+json")
    public ResponseEntity<EntityModel<ArrayWrapper>> getBubble(@PathVariable String id) {
        ArrayWrapper wrapper = arraysRepository.get(Integer.parseInt(id));
        sortService.sortInput("bubble", wrapper);
        EntityModel<ArrayWrapper> arrayWrapperEntityModel = EntityModel.of(wrapper, getMainLink());
        return new ResponseEntity<>(arrayWrapperEntityModel, HttpStatus.OK);
    }

    @PostMapping(value = "/sort")
    public ResponseEntity<EntityModel<ArrayWrapper>> postArray(@RequestBody int[] input) {
        var wrapper = arraysRepository.add(input);
        String id = String.valueOf(wrapper.getId());
        var bubbleLink = getBubbleLink(id);
        var quickLink = getQuickLink(id);
        wrapper.add(bubbleLink,quickLink);
        var wrapperEntityModel = EntityModel.of(wrapper, getMainLink());
        return new ResponseEntity<>(wrapperEntityModel, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/sort/{id}")
    public ResponseEntity<CollectionModel<ArrayWrapper>> deleteArray(@PathVariable String id) {
        arraysRepository.delete(Integer.parseInt(id));
        var link = getMainLink();
        return new ResponseEntity<>(CollectionModel.of(arraysRepository.getArrays(), link), HttpStatus.OK);
    }


    private Link getBubbleLink(String id) {
        return linkTo(methodOn(SortController.class).getBubble(id))
                .withRel("bubble").withType("application/vnd.ctm.v1+json");
    }

    private Link getQuickLink(String id) {
        return linkTo(methodOn(SortController.class).getQuick(id))
                .withRel("quick").withType("application/vnd.ctm.v2+json");
    }

    private Link getMainLink(){
        return linkTo(SortController.class).slash("sort").withSelfRel();
    }
}
