package foundation.watchers

import io.github.alaksion.invoicer.foundation.ui.events.EventAware
import io.github.alaksion.invoicer.foundation.ui.events.EventPublisher

class RefreshBeneficiaryPublisher : EventAware<Unit> by EventPublisher()
