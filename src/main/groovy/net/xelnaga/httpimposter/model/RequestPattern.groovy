package net.xelnaga.httpimposter.model

import groovy.transform.EqualsAndHashCode
import net.xelnaga.httpimposter.StringPrinter

@EqualsAndHashCode
class RequestPattern {

    String uri
    String method
    Set<HttpHeader> headers = [] as TreeSet
    String body

    @Override
    String toString() {

        StringPrinter printer = new StringPrinter()
        return printer.print(this)
    }
}