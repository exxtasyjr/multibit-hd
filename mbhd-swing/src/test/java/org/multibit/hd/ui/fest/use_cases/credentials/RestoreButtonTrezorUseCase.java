package org.multibit.hd.ui.fest.use_cases.credentials;

import org.fest.swing.fixture.FrameFixture;
import org.multibit.hd.core.dto.CoreMessageKey;
import org.multibit.hd.ui.fest.use_cases.AbstractFestUseCase;
import org.multibit.hd.ui.languages.Languages;
import org.multibit.hd.ui.languages.MessageKey;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.fest.swing.timing.Timeout.timeout;

/**
 * <p>Use case to provide the following to FEST testing:</p>
 * <ul>
 * <li>Click "restore" button</li>
 * </ul>
 *
 * @since 0.0.1
 *
 */
public class RestoreButtonTrezorUseCase extends AbstractFestUseCase {

  public RestoreButtonTrezorUseCase(FrameFixture window) {
    super(window);
  }

  @Override
  public void execute(Map<String, Object> parameters) {
   // Fixed time to unlock
    pauseForWalletUnlock();

    // Test for successful wallet load
    assertLabelContainsValue(
      CoreMessageKey.WALLET_LOADING.getKey(),
      Languages.safeText(CoreMessageKey.WALLET_LOADED_OK));

    // Wait for Finish button to appear
    window
      .button(MessageKey.FINISH.getKey())
      .requireVisible()
        // Allow a short time to overcome initialisation delays
      .requireEnabled(timeout(1, TimeUnit.SECONDS));

    // Expect the restore button
    window
      .button(MessageKey.RESTORE.getKey())
      .click();

    // Allow time for the wizard hand over to take place
    pauseForViewReset();

    // Click on the next button, which selects the last backup and starts the actual restore
    window
      .button(MessageKey.NEXT.getKey())
      .click();
  }

}
