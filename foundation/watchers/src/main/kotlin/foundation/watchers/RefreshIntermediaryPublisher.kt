package foundation.watchers

import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher

class RefreshIntermediaryPublisher : EventAware<Unit> by EventPublisher()
