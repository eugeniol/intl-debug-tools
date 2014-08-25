import org.codehaus.groovy.grails.commons.TagLibArtefactHandler

/**
 * Created with IntelliJ IDEA.
 * User: uke
 * Date: 23/08/14
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
class UiDebugHelperTagLib {
    static namespace = "ui"

    def uiDebugHelperService

    Boolean debugEnabled() {
        true
    }

    def debugMode = { attrs ->
        if (debugEnabled()) {
            r.require(module: 'mtvn-ui-debug');


        }
    }

    private def _uiHelper

    private def getUiHelper() {
        if (_uiHelper == null) {
            r.require(module: 'intl-debug-tools')




            def uiArtefact = grailsApplication.
                    getArtefacts(TagLibArtefactHandler.TYPE).find { it.name.endsWith('UiHelper') }

            def name = uiArtefact.getClazz().canonicalName

            _uiHelper = grailsAttributes.applicationContext.getBean(name)
        }

        return _uiHelper
    }

    private Object getItem(Map attrs) {
        def item = uiHelper.getItem(attrs)


        if (item) {
            List debugItems = request.debugItems
            if (debugItems == null) {
                debugItems = request.debugItems = []
            }


            if (!debugItems.contains(item)) {
                request.debugItems << item
                out << '<script>' <<
		                'var debugItems = debugItems || {};\n' <<
		                'debugItems["' << item.uuid << '"] = ' << item.encodeAsJSON() << ';\n' <<
                        '</script>'


            }

        }

//        uiDebugHelperService.notifyGetItem(item)
        return item
    }

    def itemTitle = { attrs, body ->
        debuggedMethod('itemTitle', attrs, body)
    }
    def itemDescription = { attrs, body ->
        debuggedMethod('itemDescription', attrs, body)
    }

    def itemImage = { attrs, body ->
        debuggedMethod('itemImage', attrs, body)
    }

    def link = { attrs, body ->
        debuggedMethod('link', attrs, body)
    }

    def template = { attrs, body ->
        debuggedMethod('template', attrs, body)
    }

//        { attrs, body ->
//
//
//        r.require(module: 'intl-debug-tools')
//
    //my changes in tag's behaviour
//        ValidationTagLib validationTagLib = grailsAttributes.applicationContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ValidationTagLib')
//        validationTagLib.message.call(attrs)
//        uiHelper.itemTitle.call(attrs, body)
//    }


    void debuggedMethod(String name, attrs, body) {
        r.require(module: 'intl-debug-tools')

        out << '<!--  debug enabled -->'
        if (debugEnabled()) {
            def item = getItem(attrs)
            if (item) {
                out << '<span data-debug-id="' << item.uuid << '" data-method="' << name << '">'
                uiHelper[name].call(attrs, body)
                out << '</span>'
                return
            }
        }
        uiHelper[name].call(attrs, body)
    }


}