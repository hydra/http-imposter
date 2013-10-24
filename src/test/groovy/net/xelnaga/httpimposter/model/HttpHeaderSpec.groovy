package net.xelnaga.httpimposter.model

import spock.lang.Specification

class HttpHeaderSpec extends Specification {

    def 'equals and hashcode'() {
    
        given:
            DefaultHttpHeader header1 = new DefaultHttpHeader(name1, value1)
            DefaultHttpHeader header2 = new DefaultHttpHeader(name2, value2)
        
        expect:
            header1.equals(header2) == result
            (header1.hashCode() == header2.hashCode()) == result

        where:
            name1          | name2          | value1       | value2       | result
            'Content-Type' | 'Content-Type' | 'text/plain' | 'text/plain' | true
            'Content-Type' | 'content-type' | 'text/plain' | 'text/plain' | true
            'Content-Type' | 'Content-Type' | 'Text/Plain' | 'text/plain' | false
            'Content-Type' | 'content-type' | 'Text/Plain' | 'text/plain' | false
    }
    
    def 'equals with same instance'() {
        
        given:
            DefaultHttpHeader httpHeader = new DefaultHttpHeader('Hello', 'world')
        
        expect:
            httpHeader.equals(httpHeader)
    }
    
    def 'equals with object of another type'() {
        
        given:
            DefaultHttpHeader httpHeader = new DefaultHttpHeader('Hello', 'world')
            String value = 'qwerty'
        
        expect:
            !httpHeader.equals(value)
    }
    
    def 'to string'() {
        
        given:
            DefaultHttpHeader httpHeader = new DefaultHttpHeader('Some-Name', 'some-value')
            
        expect:
            httpHeader.toString() == 'Some-Name: some-value'
    }
    
    def 'compare to'() {
        
        given:
            List<HttpHeader> headers = [
                    new DefaultHttpHeader('Content-Type', 'text/plain'),
                    new DefaultHttpHeader('content-type', 'text/plain'),
                    new DefaultHttpHeader('Accept', 'application/json'),
                    new DefaultHttpHeader('Accept', 'text/plain')
            ]
        
        when:
            TreeSet<HttpHeader> treeSet = [] as TreeSet
            headers.each { HttpHeader httpHeader ->
                treeSet << httpHeader    
            }
        
        then:
            treeSet as List == [ headers[2], headers[3], headers[1] ]
    }
}
