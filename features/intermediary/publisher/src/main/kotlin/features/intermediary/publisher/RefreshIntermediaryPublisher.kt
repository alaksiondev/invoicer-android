package features.intermediary.publisher

import foundation.events.EventAware
import foundation.events.EventPublisher

class RefreshIntermediaryPublisher : EventAware<Unit> by EventPublisher()