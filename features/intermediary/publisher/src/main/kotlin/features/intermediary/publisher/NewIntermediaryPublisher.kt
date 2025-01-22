package features.intermediary.publisher

import foundation.events.EventAware
import foundation.events.EventPublisher

class NewIntermediaryPublisher : EventAware<Unit> by EventPublisher()