package edu.school21.processors;

import com.google.auto.service.AutoService;
import edu.school21.annotations.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.*;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = e.getAnnotation(HtmlForm.class);
            String htmlFileName = htmlForm.fileName();

            try {
                FileObject fileObject = processingEnv.getFiler().createResource(
                        StandardLocation.CLASS_OUTPUT, "", htmlFileName);
                try (BufferedWriter writer = new BufferedWriter(fileObject.openWriter())) {
                    writer.write("<form action=\"" + htmlForm.action() + "\" method=\"" + htmlForm.method() + "\">\n");

                    for (Element enclosedElement : e.getEnclosedElements()) {
                        if (enclosedElement.getKind().isField() && enclosedElement.getAnnotation(HtmlInput.class) != null) {
                            HtmlInput htmlInput = enclosedElement.getAnnotation(HtmlInput.class);
                            writer.write("  <input type=\"" + htmlInput.type() + "\" name=\"" + htmlInput.name() + "\" placeholder=\"" + htmlInput.placeholder() + "\">\n");
                        }
                    }

                    writer.write("  <input type=\"submit\" value=\"Send\">\n");
                    writer.write("</form>\n");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        return true;
    }

}
