//
//  Attack.h
//  AttackMonitor
//
//  Created by MacBookPro on 6/19/14.
//  Copyright (c) 2014 HAU. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Attack : NSObject

@property (nonatomic, strong) NSString *suspiciousIp;
@property (nonatomic, strong) NSNumber *threatId;

@end