package trapx00.lightx00.client.presentation.helpui;

import com.jfoenix.controls.JFXSnackbar;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import trapx00.lightx00.client.Client;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AboutPageController implements ExternalLoadableUiController {
    public Label textLicense;
    public String licenseText;

    public void onRepositoryAddressClicked(MouseEvent mouseEvent) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("http://101.37.19.32:10080/trap-x00/ERPnju"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() throws IOException {

        licenseText = "Copyright (c) 2018, Trap x00\n" +
            "All rights reserved.\n" +
            "\n" +
            "Redistribution and use in source and binary forms, with or without\n" +
            "modification, are permitted provided that the following conditions are met:\n" +
            "1. Redistributions of source code must retain the above copyright\n" +
            "   notice, this list of conditions and the following disclaimer.\n" +
            "2. Redistributions in binary form must reproduce the above copyright\n" +
            "   notice, this list of conditions and the following disclaimer in the\n" +
            "   documentation and/or other materials provided with the distribution.\n" +
            "3. All advertising materials mentioning features or use of this software\n" +
            "   must display the following acknowledgement:\n" +
            "   This product includes software developed by the Trap x00.\n" +
            "4. Neither the name of the Trap x00 nor the\n" +
            "   names of its contributors may be used to endorse or promote products\n" +
            "   derived from this software without specific prior written permission.\n" +
            "\n" +
            "\n" +
            "THIS SOFTWARE IS PROVIDED BY TRAP x00 ''AS IS'' AND ANY\n" +
            "EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED\n" +
            "WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE\n" +
            "DISCLAIMED. IN NO EVENT SHALL TRAP x00 BE LIABLE FOR ANY\n" +
            "DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES\n" +
            "(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;\n" +
            "LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND\n" +
            "ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n" +
            "(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS\n" +
            "SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.";
        textLicense.setText(licenseText);

    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/helpui/AboutPage.fxml").loadAndGetPackageWithoutException();
    }

    public void onLabelClicked(MouseEvent mouseEvent) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(licenseText);
        clipboard.setContent(content);
        JFXSnackbar bar = new JFXSnackbar(FrameworkUiManager.getDialogContainer());
        bar.enqueue(new JFXSnackbar.SnackbarEvent("复制成功","-fx-background-color: white;"));
    }
}
