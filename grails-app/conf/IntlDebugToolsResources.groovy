def pluginName = 'intl-debug-tools'
modules = {
    'intl-debug-tools' {



        resource url: [file: 'underscore-1.6.0.js', dir: 'js/libs', plugin: pluginName]
        resource url: [file: 'backbone-1.1.2.js', dir: 'js/libs', plugin: pluginName]
        resource url: [file: 'mtvni.ui.debug.js', dir: 'js', plugin: pluginName]


//        resource url: [file: 'debug.css', dir: 'css', plugin: 'intl-debug-tools']

    }
}