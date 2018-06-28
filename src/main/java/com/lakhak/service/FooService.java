package com.lakhak.service;

import com.lakhak.model.Bar;
import com.lakhak.model.Foo;

import java.util.List;

import static java.util.Arrays.asList;

public class FooService {

    public List<Foo> allFoos() {
        return asList(
                new Foo(1L, new Bar(1111L, "abc")),
                new Foo(2L, new Bar(2222L, "def")),
                new Foo(3L, new Bar(3333L, "ghi")),
                new Foo(4L, new Bar(4444L, "jkl"))
        );
    }
}
