package features.intermediary.publisher

import foundation.ui.events.EventAware
import foundation.ui.events.EventPublisher

class RefreshIntermediaryPublisher : foundation.ui.events.EventAware<Unit> by foundation.ui.events.EventPublisher()