import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.*
import com.vanpra.composematerialdialogs.DesktopWindowPosition
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.nio.file.Path
import javax.swing.JOptionPane


@Composable
fun showFileDialog(
    showDialogState: () -> Unit,
    title: String,
    isLoad: Boolean,
    onResult: (result: Path?) -> Unit,
) {

    Window(
        onCloseRequest = {
            showDialogState()
        },
        title = title,
        state = rememberWindowState(
            height = Dp.Unspecified,
            position = WindowPosition(Alignment.Center)
        ),
    ) {

        FileDialog(title = title, isLoad = isLoad, onResult = onResult)
    }
}

@Composable
fun FileDialog( //FrameWindowScope.
    parent: Frame? = null,
    title: String,
    isLoad: Boolean,
    onResult: (result: Path?) -> Unit
) = AwtWindow(
    create = {
        object : FileDialog(parent, "Choose a file", if (isLoad) LOAD else SAVE) { //window //parent
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    if (directory != null) {
                        onResult(File(directory).toPath()) // File(directory).resolve(file).toPath()
                    } else {
                        onResult(null)
                    }
                }
            }
        }.apply {
//            this.iconImages = painterResource("drawables/logo.png") //Icons.Default.Menu//painterResource("drawables/logo.png")
            this.title = title
            this.file = "" // to select only folder
            this.directory
//            this.location = DesktopWindowPosition(Alignment.Center)
//            this.fileFilter = FileNameExtensionFilter("folders", "") // to

//            painterResource("drawables/logo.png")


        }
    },
    dispose = FileDialog::dispose
)

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun WindowScope.YesNoCancelDialog(
    title: String,
    message: String,
    onResult: (result: AlertDialogResult) -> Unit
) {
    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.Swing) {
            val resultInt = JOptionPane.showConfirmDialog(
                window, message, title, JOptionPane.YES_NO_CANCEL_OPTION
            )
            val result = when (resultInt) {
                JOptionPane.YES_OPTION -> AlertDialogResult.Yes
                JOptionPane.NO_OPTION -> AlertDialogResult.No
                else -> AlertDialogResult.Cancel
            }
            onResult(result)
        }

        onDispose {
            job.cancel()
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Preview
@Composable
fun WindowScope.inputDialog(
    title: String,
    message: String,
    onResult: (result: AlertDialogResult) -> Unit
) {
    DisposableEffect(Unit) {
        val job = GlobalScope.launch(Dispatchers.Swing) {
            val resultInt = JOptionPane.showConfirmDialog(
                window, message, title, JOptionPane.YES_NO_CANCEL_OPTION
            )
            val result = when (resultInt) {
                JOptionPane.YES_OPTION -> AlertDialogResult.Yes
                JOptionPane.NO_OPTION -> AlertDialogResult.No
                else -> AlertDialogResult.Cancel
            }
            onResult(result)
        }

        onDispose {
            job.cancel()
        }
    }
}
//
//@Composable
//fun WindowScope.CustomDialogScrollable(
//    onConfirmClicked: () -> Unit,
//    onDismiss: () -> Unit,
//) {
//    Dialog(
//        onDismissRequest = onDismiss,
//    ) {
//        Surface(
//            shape = MaterialTheme.shapes.medium,
//            color = MaterialTheme.colors.surface,
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                // TITLE
//                Text(text = "Title", style = MaterialTheme.typography.subtitle1)
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .verticalScroll(rememberScrollState())
//                        .weight(weight = 1f, fill = false)
//                        .padding(vertical = 16.dp)
//                ) {
//                    Text(
//                        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
//                        style = MaterialTheme.typography.body2
//                    )
//                    OutlinedTextField(value = path, onValueChange = {
//                    }, Modifier.padding(top = 8.dp), label = { Text(text = "Email") })
//
//                    // other content can go here
//                }
//
//                // BUTTONS
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
//                    TextButton(onClick = onDismiss) {
//                        Text(text = "Cancel")
//                    }
//                    TextButton(onClick = onConfirmClicked) {
//                        Text(text = "OK")
//                    }
//                }
//            }
//        }
//    }
//}

enum class AlertDialogResult {
    Yes, No, Cancel
}