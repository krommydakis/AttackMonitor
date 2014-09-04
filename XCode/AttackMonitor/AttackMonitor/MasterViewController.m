//
//  MasterViewController.m
//  AttackMonitor
//
//  Created by MacBookPro on 6/19/14.
//  Copyright (c) 2014 HAU. All rights reserved.
//

#import "MasterViewController.h"


#import <RestKit/RestKit.h>
#import "Attack.h"

@interface MasterViewController ()

@property (nonatomic, strong) NSArray *attacks;

@end

@implementation MasterViewController

- (void)awakeFromNib
{
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        self.clearsSelectionOnViewWillAppear = NO;
        self.preferredContentSize = CGSizeMake(320.0, 600.0);
    }
    [super awakeFromNib];
}

/*
-(void) configureRestKit{
    
    //initialize AFNetworking HTTPClient
    NSURL *baseURL = [NSURL URLWithString:@"http://192.168.1.30:8080/MySQLService/webresources/"];   //To be corrected per case
    AFHTTPClient *client = [[AFHTTPClient alloc] initWithBaseURL:baseURL];
    
    
    //initialize RestKit
    RKObjectManager *objectManager = [[RKObjectManager alloc]initWithHTTPClient:client];
    [objectManager setAcceptHeaderWithMIMEType:@"application/json"];
    
    //setup object mappings
    RKObjectMapping *propertyMapping = [RKObjectMapping mappingForClass:[Attack class]];
    //[propertyMapping addAttributeMappingsFromArray:@[@"suspiciousIp"]];
    [propertyMapping addAttributeMappingsFromDictionary:@{@"suspiciousIp": @"suspiciousIp", @"threatId": @"threatId",}];
    
    //register mappings with the provider using a response descriptor
    RKResponseDescriptor *responseDescriptor = [RKResponseDescriptor responseDescriptorWithMapping:propertyMapping method:RKRequestMethodGET pathPattern:nil keyPath:@"suspiciousIp" statusCodes:[NSIndexSet indexSetWithIndex:200]];
    
    [objectManager addResponseDescriptor:responseDescriptor];
    
//    RKObjectRequestOperation *objectRequestOperation = [[RKObjectRequestOperation alloc] initWithRequest:request responseDescriptors:@[ responseDescriptor ]];
//    [objectRequestOperation setCompletionBlockWithSuccess:^(RKObjectRequestOperation *operation, RKMappingResult *mappingResult) {
//        RKLogInfo(@"Load collection of Attacks: %@", mappingResult.array);
//    } failure:^(RKObjectRequestOperation *operation, NSError *error) {
//        RKLogError(@"Operation failed with error: %@", error);
//    }];
//    
//    [objectRequestOperation start];
}

-(void)loadAttacks
{
   // NSString *suspiciousIp = @"suspiciousIp";
    
  //  NSDictionary *queryParams = @{
  //                               @"suspiciousIp":suspiciousIp };
    
    [[RKObjectManager sharedManager] getObjectsAtPath:@"com.mysql.entities.threat" parameters:nil//queryParams
                                              success:^(RKObjectRequestOperation *operation, RKMappingResult *mappingResult)
                                            {
                                                    NSLog(@"SUCCESS !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                    _attacks = mappingResult.array;
                                                    [self.tableView reloadData];
     
                                            }
                                              failure:^(RKObjectRequestOperation *operation, NSError *error) {
                                                  NSLog(@"ERROR: %@", error);
                                             }];
 
    
}
*/

-(void)loadAttacks
{
    RKObjectMapping* attackMapping = [RKObjectMapping mappingForClass:[Attack class]];
    [attackMapping addAttributeMappingsFromDictionary:@{@"suspiciousIp": @"suspiciousIp", @"threatId": @"threatId"}];
    
    RKResponseDescriptor *responseDescriptor = [RKResponseDescriptor responseDescriptorWithMapping:attackMapping method:RKRequestMethodAny pathPattern:nil keyPath:nil statusCodes:RKStatusCodeIndexSetForClass(RKStatusCodeClassSuccessful)];
    
    NSURL *URL = [NSURL URLWithString:@"http://192.168.1.14:8080/MySQLService/webresources/com.mysql.entities.threat"];
    
    //NSURLRequest *request = [NSURLRequest requestWithURL:URL];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    [request setHTTPMethod:@"GET"];
    [request setValue:@"application/json" forHTTPHeaderField:@"Accept"];
    
    RKObjectRequestOperation *objectRequestOperation = [[RKObjectRequestOperation alloc] initWithRequest:request responseDescriptors:@[ responseDescriptor ]];
    [objectRequestOperation setCompletionBlockWithSuccess:^(RKObjectRequestOperation *operation, RKMappingResult *mappingResult) {
        RKLogInfo(@"Load collection of Attacks: %@", mappingResult.array);
        _attacks = mappingResult.array;
        [self.tableView reloadData];
        
    } failure:^(RKObjectRequestOperation *operation, NSError *error) {
        RKLogError(@"Operation failed with error: %@", error);
    }];
    
    [objectRequestOperation start];
}
    





- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
   // [self configureRestKit];
    [self loadAttacks];
    
    
    
    
//    self.navigationItem.leftBarButtonItem = self.editButtonItem;
//
//    UIBarButtonItem *addButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAdd target:self action:@selector(insertNewObject:)];
//    self.navigationItem.rightBarButtonItem = addButton;
//    self.detailViewController = (DetailViewController *)[[self.splitViewController.viewControllers lastObject] topViewController];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

//- (void)insertNewObject:(id)sender
//{
//    if (!_objects) {
//        _objects = [[NSMutableArray alloc] init];
//    }
//    [_objects insertObject:[NSDate date] atIndex:0];
//    NSIndexPath *indexPath = [NSIndexPath indexPathForRow:0 inSection:0];
//    [self.tableView insertRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
//}

#pragma mark - Table View

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _attacks.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell" forIndexPath:indexPath];

    Attack *attack = _attacks[indexPath.row];
    cell.textLabel.text = attack.suspiciousIp;
    return cell;
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}






@end
