package net.xelnaga.httpimposter.model

class RegexMatchingHttpHeader extends BaseHttpHeader {

    final String name
    final String value

    RegexMatchingHttpHeader(String name, String regularExpression) {
        this.name = name
        this.value = regularExpression
    }

    @Override
    String getType() {
        return 'regex'
    }

    @Override
    boolean compareValue(String otherValue) {
        otherValue ==~ value
    }

}
