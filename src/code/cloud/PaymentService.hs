{-# LANGUAGE GHC2024 #-}
{-# LANGUAGE DeriveAnyClass #-}

module PaymentService where

import AWS.Lambda.Runtime (ioRuntime)
import Data.Aeson
import Data.UUID.V4 (nextRandom)
import Data.UUID (toString)
import GHC.Generics (Generic)

data OrderCreatedEvent = OrderCreatedEvent
  { orderId :: String
  , userId  :: String
  , amount  :: Double
  } deriving stock (Show, Generic)
  deriving anyclass (FromJSON, ToJSON)

data Payment = Payment
  { id             :: String
  , paymentOrderId :: String
  , success        :: Bool
  } deriving stock (Show, Generic)
  deriving anyclass (FromJSON, ToJSON)

orderCreatedEventHandler :: OrderCreatedEvent -> IO ()
orderCreatedEventHandler event = do
  uId <- nextRandom
  let payment = Payment { id = toString uId, paymentOrderId = orderId event, success = True }
  -- imagine here that payment always succeeds and we serialize it successfully
  -- imagine further serialization into e.g. Amazon RDS which would trigger a PaymentProcessedEvent
  return ()

-- entry point for aws lambda
 main :: IO ()
 main = ioRuntime $ fmap Right . orderCreatedEventHandler