package net.xelnaga.httpimposter.factory

import net.xelnaga.httpimposter.filter.HttpHeaderFilter
import net.xelnaga.httpimposter.filter.PassThroughFilter
import net.xelnaga.httpimposter.model.DefaultHttpHeader
import net.xelnaga.httpimposter.model.HttpHeader
import net.xelnaga.httpimposter.model.RequestPattern

import javax.servlet.http.HttpServletRequest

class RequestPatternFactory {

    HttpHeaderFilter filter = new PassThroughFilter()

    RequestPattern fromHttpRequest(HttpServletRequest request) {

        String uri = request.requestURI
        if (request.queryString) {
            uri += "?${request.queryString}"
        }

        RequestPattern pattern = new RequestPattern(
                uri:    uri,
                method: request.method,
                body:   request.inputStream.text)
        
        request.headerNames.each { String name ->

            String value = request.getHeader(name)
            DefaultHttpHeader httpHeader = new DefaultHttpHeader(name, value)
            addMatchableHeader(pattern, httpHeader)
        }
        
        return pattern
    }

    private void addMatchableHeader(RequestPattern pattern, HttpHeader header) {

        if (filter.isMatchable(header)) {
            pattern.headers << header
        }
    }
}
