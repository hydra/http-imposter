package net.xelnaga.httpimposter.model

import groovy.transform.TupleConstructor

class DefaultHttpHeader extends BaseHttpHeader {

    final String name
    final String value

    DefaultHttpHeader(String name, String value) {
        this.name = name
        this.value = value
    }

    @Override
    String getType() {
        return 'default'
    }

    @Override
    boolean compareValue(String otherValue) {
        otherValue == value
    }
}
