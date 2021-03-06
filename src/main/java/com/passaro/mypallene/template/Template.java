package com.passaro.mypallene.template;

import java.util.Optional;

public interface Template<T> {

    void write(String filePath, T mode);

    Optional<T> create();

}
