package me.aartikov.alligatorcompiler;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class ErrorReporter {
	private final Messager messager;

    ErrorReporter(ProcessingEnvironment processingEnv) {
        this.messager = processingEnv.getMessager();
    }

    /**
     * Issue a compilation note.
     *
     * @param msg the text of the note
     * @param e   the element to which it pertains
     */
    public void reportNote(String msg, Element e) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg, e);
    }

    /**
     * Issue a compilation warning.
     *
     * @param msg the text of the warning
     * @param e   the element to which it pertains
     */
    public void reportWarning(String msg, Element e) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg, e);
    }

    /**
     * Issue a compilation error. This method does not throw an exception, since we want to continue
     * processing and perhaps report other errors. It is a good idea to introduce a test case in
     * CompilationTest for any new call to reportError(...) to ensure that we continue correctly after
     * an error.
     *
     * @param msg the text of the warning
     * @param e   the element to which it pertains
     */
    public void reportError(String msg, Element e) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
}