package foundation.watchers

import io.github.alaksion.invoicer.foundation.ui.events.EventAware
import io.github.alaksion.invoicer.foundation.ui.events.EventPublisher

class RefreshIntermediaryPublisher : EventAware<Unit> by EventPublisher()
