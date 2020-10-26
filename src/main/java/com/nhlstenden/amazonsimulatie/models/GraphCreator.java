package com.nhlstenden.amazonsimulatie.models;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

interface GraphCreator {

    /**
     * Creates and returns a new Graph
     * @return A new Graph
     */
    Graph getGraph();


}
